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
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    
    private lateinit var tvDialedNumber: TextView
    private lateinit var dialerLayout: ConstraintLayout
    private lateinit var fragmentContainer: FrameLayout
    private var currentNumber = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. App start hote hi saari Permissions mangna (Crucial for Assignment)
        PermissionManager.checkAndRequestPermissions(this)

        // UI Binding
        tvDialedNumber = findViewById(R.id.tvDialedNumber)
        dialerLayout = findViewById(R.id.dialer_layout) // Ye tera original dialer layout hai
        fragmentContainer = findViewById(R.id.fragment_container)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        setupDialButtons()

        // 2. Bottom Navigation Logic (Fragment Switching)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dialer -> {
                    // Dialer dikhao, baki fragments hide karo
                    dialerLayout.visibility = View.VISIBLE
                    fragmentContainer.visibility = View.GONE
                    true
                }
                R.id.nav_logs -> {
                    loadFragment(CallLogFragment())
                    true
                }
                R.id.nav_contacts -> {
                    loadFragment(ContactFragment())
                    true
                }
                else -> false
            }
        }

        // 3. Backspace Logic
        findViewById<ImageButton>(R.id.btnBackspace).setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                currentNumber.deleteAt(currentNumber.length - 1)
                tvDialedNumber.text = currentNumber.toString()
            }
        }

        // 4. REAL CALL BUTTON (Intent.ACTION_CALL)
        findViewById<View>(R.id.btnMakeCall).setOnClickListener {
            val target = currentNumber.toString()
            if (target.isNotEmpty()) {
                makeRealPhoneCall(target)
            } else {
                Toast.makeText(this, "Pehle number dial karein!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fragment load karne ka function
    private fun loadFragment(fragment: Fragment) {
        dialerLayout.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun makeRealPhoneCall(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")

        // Permission Check before triggering the call
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent) // Ye asli calling screen pe le jayega
        } else {
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
                if (currentNumber.length < 15) { 
                    currentNumber.append(btn.text)
                    tvDialedNumber.text = currentNumber.toString()
                }
            }
        }
    }
}
