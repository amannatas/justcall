package com.example.justcall

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Outgoing_Call_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outgoing_call_screen)

        val number = intent.getStringExtra("PHONE_NUMBER")
        findViewById<TextView>(R.id.tvDialedNumber).text = number

        // 3 Second baad apne aap Active screen par le jao (Simulation)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Incoming_Call_Screen::class.java)
            intent.putExtra("PHONE_NUMBER", number)
            startActivity(intent)
            finish()
        }, 3000)

        findViewById<FloatingActionButton>(R.id.btnEndCall).setOnClickListener {
            finish() // Call cancel
        }
    }
}