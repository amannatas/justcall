package com.example.justcall

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CallLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listView = ListView(this) // Simple ListView UI
        setContentView(listView)

        try {
            val logs = CallLogHelper(this).getAllCallLogs()
            val displayList = logs.map { "${it.name ?: it.number}\n${it.type} | ${it.date}" }
            
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)
            listView.adapter = adapter

            [span_1](start_span)// Tapping a log should initiate a real call[span_1](end_span)
            listView.setOnItemClickListener { _, _, position, _ ->
                val number = logs[position].number
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:$number")
                startActivity(intent)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Permission check karo!", Toast.LENGTH_SHORT).show()
        }
    }
}
