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

class ContactFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_contact, container, false)
        val listView = view.findViewById<ListView>(R.id.lvContacts)

        val contacts = ContactHelper(requireContext()).getAllContacts()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, 
            contacts.map { "${it.name}\n${it.number}" })
        
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${contacts[position].number}")
            startActivity(intent)
        }
        return view
    }
}
