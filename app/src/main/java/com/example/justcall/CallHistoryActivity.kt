package com.example.justcall

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class CallHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listView = ListView(this)
        setContentView(listView)

        val logs = CallLogHelper(this).getAllCallLogs()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, 
            logs.map { "${it.name ?: it.number} - ${it.type} (${it.duration})" })
        
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${logs[position].number}")
            startActivity(intent)
        }
    }
}
