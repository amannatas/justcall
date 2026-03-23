package com.example.justcall

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var tvDialedNumber: TextView
    private var currentNumber = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDialedNumber = findViewById(R.id.tvDialedNumber)

        setupDialButtons()

        findViewById<ImageButton>(R.id.btnBackspace).setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                currentNumber.deleteAt(currentNumber.length - 1)
                tvDialedNumber.text = currentNumber.toString()
            }
        }

        findViewById<FloatingActionButton>(R.id.btnMakeCall).setOnClickListener {
            val target = currentNumber.toString()
            if (target.isNotEmpty()) {
                val intent = Intent(this, Outgoing_Call_Screen::class.java)
                intent.putExtra("PHONE_NUMBER", target)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Number enter karo bhai!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupDialButtons() {
        val buttons = listOf(R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnStar, R.id.btnHash)

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                val btn = it as Button
                if (currentNumber.length < 10) {
                    currentNumber.append(btn.text)
                    tvDialedNumber.text = currentNumber.toString()
                } else {
                    Toast.makeText(this, "Max 10 digits allowed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}