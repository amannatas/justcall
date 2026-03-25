package com.example.justcall

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var tvDialedNumber: TextView
    private var currentNumber = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pehle saari permissions mang lo (Assignment requirement)
        PermissionManager.checkAndRequestPermissions(this)

        tvDialedNumber = findViewById(R.id.tvDialedNumber)
        setupDialButtons()

        // Backspace logic
        findViewById<ImageButton>(R.id.btnBackspace).setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                currentNumber.deleteAt(currentNumber.length - 1)
                tvDialedNumber.text = currentNumber.toString()
            }
        }

        // REAL CALL BUTTON LOGIC
        findViewById<FloatingActionButton>(R.id.btnMakeCall).setOnClickListener {
            val target = currentNumber.toString()
            if (target.isNotEmpty()) {
                makeRealPhoneCall(target)
            } else {
                Toast.makeText(this, "Number enter karo bhai!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makeRealPhoneCall(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")

        // Permission check before calling (Mandatory for Android)
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent) // Ye asli call lagayega
        } else {
            // Agar permission nahi hai toh maango
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 101)
        }
    }

    private fun setupDialButtons() {
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnStar, R.id.btnHash
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                val btn = it as Button
                // Phone numbers can be longer than 10 digits (like international), 
                // par assignment ke liye simple rakhte hain.
                if (currentNumber.length < 15) { 
                    currentNumber.append(btn.text)
                    tvDialedNumber.text = currentNumber.toString()
                }
            }
        }
    }
}
