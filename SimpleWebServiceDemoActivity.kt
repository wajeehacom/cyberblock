package com.example.webview

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SimpleWebServiceDemoActivity : Activity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_web_service_demo)

        textView = findViewById(R.id.textView)

        // Fetch data using coroutines
        GlobalScope.launch {
            try {
                val students = RetrofitInstance.api.getAllStudents()
                val studentInfo = students.joinToString("\n") { student ->
                    " Name: ${student.name}, Roll Number: ${student.rollnumber},Age: ${student.age}, CGPA: ${student.cgpa}"
                }
                runOnUiThread {
                    textView.text = studentInfo
                }
            } catch (e: HttpException) {
                // Handle HTTP errors (e.g., 404, 500)
                runOnUiThread {
                    textView.text = "HTTP error: ${e.code()} ${e.message()}"
                }
            } catch (e: IOException) {
                // Handle network errors (e.g., no internet)
                runOnUiThread {
                    textView.text = "Network error: ${e.message}"
                }
            } catch (e: Exception) {
                runOnUiThread {
                    textView.text = "Error fetching data"
                }
            }
        }
    }
}
