package com.example.webview
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.webview.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WebServiceListViewDemoActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_service_list_view_demo)

        listView = findViewById(R.id.listViewStudents)

        // Fetch data using coroutines
        GlobalScope.launch {
            try {
                val students = RetrofitInstance.api.getAllStudents()
                val studentNames = students.map { student ->
                    "Name: ${student.name}, Roll Number: ${student.rollnumber}, Age: ${student.age}, CGPA: ${student.cgpa}"
                }
                runOnUiThread {
                    val adapter = ArrayAdapter(this@WebServiceListViewDemoActivity, android.R.layout.simple_list_item_1, studentNames)
                    listView.adapter = adapter
                }
            } catch (e: HttpException) {
                runOnUiThread {
                    val errorList = listOf("HTTP error: ${e.code()} ${e.message()}")
                    listView.adapter = ArrayAdapter(this@WebServiceListViewDemoActivity, android.R.layout.simple_list_item_1, errorList)
                }
            } catch (e: IOException) {
                runOnUiThread {
                    val errorList = listOf("Network error: ${e.message}")
                    listView.adapter = ArrayAdapter(this@WebServiceListViewDemoActivity, android.R.layout.simple_list_item_1, errorList)
                }
            } catch (e: Exception) {
                runOnUiThread {
                    val errorList = listOf("Error fetching data")
                    listView.adapter = ArrayAdapter(this@WebServiceListViewDemoActivity, android.R.layout.simple_list_item_1, errorList)
                }
            }
        }
    }
}
