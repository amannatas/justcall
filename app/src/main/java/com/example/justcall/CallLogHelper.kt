package com.example.justcall

import android.content.Context
import android.provider.CallLog
import java.util.*

class CallLogHelper(private val context: Context) {
    fun getAllCallLogs(): List<CallLogModel> {
        val itemList = mutableListOf<CallLogModel>()
        val cursor = context.contentResolver.query(
            CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC"
        )

        cursor?.use {
            val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
            val typeIndex = it.getColumnIndex(CallLog.Calls.TYPE)
            val dateIndex = it.getColumnIndex(CallLog.Calls.DATE)
            val durationIndex = it.getColumnIndex(CallLog.Calls.DURATION)
            val nameIndex = it.getColumnIndex(CallLog.Calls.CACHED_NAME)

            while (it.moveToNext()) {
                val type = when (it.getInt(typeIndex)) {
                    CallLog.Calls.INCOMING_TYPE -> "Incoming"
                    CallLog.Calls.OUTGOING_TYPE -> "Outgoing"
                    CallLog.Calls.MISSED_TYPE -> "Missed"
                    else -> "Unknown"
                }
                itemList.add(CallLogModel(
                    it.getString(nameIndex) ?: "Unknown",
                    it.getString(numberIndex),
                    type,
                    Date(it.getLong(dateIndex)).toString(),
                    "${it.getString(durationIndex)} sec"
                ))
            }
        }
        return itemList
    }
}

