package com.example.justcall

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var tvDialedNumber: TextView
    private lateinit var dialerLayout: ConstraintLayout
    private lateinit var fragmentContainer: FrameLayout
    private var currentNumber = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Mandatory Permissions Request (Assignment Rule)
        [span_2](start_span)PermissionManager.checkAndRequestPermissions(this)[span_2](end_span)

        // UI Initializations
        tvDialedNumber = findViewById(R.id.tvDialedNumber)
        dialerLayout = findViewById(R.id.dialer_layout)
        fragmentContainer = findViewById(R.id.fragment_container)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        setupDialButtons()

        // 2. Bottom Navigation Logic
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dialer -> {
                    dialerLayout.visibility = View.VISIBLE
                    // Hide any active fragment if necessary
                    true
                }
                R.id.nav_logs -> {
                    dialerLayout.visibility = View.GONE
                    // Start Call Log Activity (Simpler approach for your setup)
                    [span_3](start_span)startActivity(Intent(this, CallHistoryActivity::class.java))[span_3](end_span)
                    true
                }
                R.id.nav_contacts -> {
                    dialerLayout.visibility = View.GONE
                    // Start Contact Activity
                    [span_4](start_span)startActivity(Intent(this, ContactActivity::class.java))[span_4](end_span)
                    true
                }
                else -> false
            }
        }

        // 3. Backspace Button
        findViewById<ImageButton>(R.id.btnBackspace).setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                currentNumber.deleteAt(currentNumber.length - 1)
                tvDialedNumber.text = currentNumber.toString()
            }
        }

        // 4. REAL CALL BUTTON (Highest Priority Requirement)
        findViewById<View>(R.id.btnMakeCall).setOnClickListener {
            val target = currentNumber.toString()
            if (target.isNotEmpty()) {
                [span_5](start_span)[span_6](start_span)makeRealPhoneCall(target)[span_5](end_span)[span_6](end_span)
            } else {
                Toast.makeText(this, "Number enter karo!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makeRealPhoneCall(phoneNumber: String) {
        [span_7](start_span)val callIntent = Intent(Intent.ACTION_CALL)[span_7](end_span)
        callIntent.data = Uri.parse("tel:$phoneNumber")

        // Runtime Permission Check for Security
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            [span_8](start_span)startActivity(callIntent) // Triggers system's real dialer[span_8](end_span)
        } else {
            [span_9](start_span)ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 101)[span_9](end_span)
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
                if (currentNumber.length < 15) { 
                    currentNumber.append(btn.text)
                    tvDialedNumber.text = currentNumber.toString()
                }
            }
        }
    }
}
