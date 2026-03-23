package com.example.justcall

import android.content.Context
import android.content.Intent
import android.os.*
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Incoming_Call_Screen : AppCompatActivity() {

    private var vibrator: Vibrator? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incoming_call_screen)

        val callerId = intent.getStringExtra("CALLER_ID") ?: "Unknown"
        findViewById<TextView>(R.id.tvCallerName).text = callerId

        val btnAccept = findViewById<FloatingActionButton>(R.id.btnAccept)
        val btnReject = findViewById<FloatingActionButton>(R.id.btnReject)

        // 1. Effects Start Karo (Bounce + Vibration)
        startCallEffects(btnAccept, btnReject)

        // 2. Accept Button - Direct Click
        btnAccept.setOnClickListener {
            stopAll()
            val intent = Intent(this, Active_Call_Screen::class.java)
            intent.putExtra("PHONE_NUMBER", callerId)
            startActivity(intent)
            finish()
        }

        // 3. Reject Button - Direct Click
        btnReject.setOnClickListener {
            stopAll()
            // Stack clear karke Dialer (MainActivity) par wapas jao
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun startCallEffects(btnAccept: View, btnReject: View) {
        // Vertical Bouncing (Animations chalti rahengi jab tak click na ho)
        val bounceRunnable = object : Runnable {
            override fun run() {
                btnAccept.animate().translationY(-40f).setDuration(600).withEndAction {
                    btnAccept.animate().translationY(0f).setDuration(600).start()
                }.start()

                btnReject.animate().translationY(-40f).setDuration(600).withEndAction {
                    btnReject.animate().translationY(0f).setDuration(600).start()
                }.start()

                handler.postDelayed(this, 1400)
            }
        }
        handler.post(bounceRunnable)

        // Vibration Logic
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val pattern = longArrayOf(0, 1000, 1000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createWaveform(pattern, 0))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(pattern, 0)
        }
    }

    private fun stopAll() {
        vibrator?.cancel()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAll()
    }
}