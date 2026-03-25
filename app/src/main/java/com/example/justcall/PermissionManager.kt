package com.example.justcall

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionManager {
    private val permissions = arrayOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_CALL_LOG
    )

    fun checkAndRequestPermissions(activity: Activity) {
        val list = permissions.filter {
            ActivityCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }
        if (list.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, list.toTypedArray(), 100)
        }
    }
}
