package com.example.justcall

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listView = ListView(this)
        setContentView(listView)

        val contacts = ContactHelper(this).getAllContacts()
        val displayList = contacts.map { "${it.name}\n${it.number}" }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayList)
        listView.adapter = adapter

        [span_3](start_span)// Tapping a contact should initiate a real phone call[span_3](end_span)
        listView.setOnItemClickListener { _, _, position, _ ->
            val number = contacts[position].number
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$number")
            startActivity(intent)
        }
    }
}
