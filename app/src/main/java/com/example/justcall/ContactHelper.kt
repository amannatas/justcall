package com.example.justcall

import android.content.Context
import android.provider.ContactsContract

class ContactHelper(private val context: Context) {
    fun getAllContacts(): List<ContactModel> {
        val contactList = mutableListOf<ContactModel>()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val name = it.getString(nameIndex)
                val number = it.getString(numberIndex)
                contactList.add(ContactModel(name, number))
            }
        }
        return contactList
    }
}
