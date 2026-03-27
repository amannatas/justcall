package com.example.justcall

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.justcall.R // Ye line dhyan se check karna

class CallLogFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_call_log, container, false)
        val listView = view.findViewById<ListView>(R.id.lvCallLogs)

        val logs = CallLogHelper(requireContext()).getAllCallLogs()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, 
            logs.map { "${it.name ?: it.number}\n${it.type} | ${it.date}" })
        
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${logs[position].number}")
            startActivity(intent)
        }
        return view
    }
}
