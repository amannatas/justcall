package com.example.justcall

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.*
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Incoming_Call_Screen : AppCompatActivity() {

    private var initialX = 0f
    private var dX = 0f
    private val SWIPE_DISTANCE_REQUIRED = 250f
    private var vibrator: Vibrator? = null
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incoming_call_screen)

        val callerId = intent.getStringExtra("CALLER_ID") ?: "Unknown"
        findViewById<TextView>(R.id.tvCallerName).text = callerId

        val btnAccept = findViewById<FloatingActionButton>(R.id.btnAccept)
        val btnReject = findViewById<FloatingActionButton>(R.id.btnReject)

        startCallEffects(btnAccept, btnReject)

        btnAccept.setOnTouchListener { v, event ->
            handleSwipeLogic(v, event, true, callerId)
        }

        btnReject.setOnTouchListener { v, event ->
            handleSwipeLogic(v, event, false, callerId)
        }
    }

    private fun handleSwipeLogic(view: View, event: MotionEvent, isAccept: Boolean, callerId: String): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = view.x
                dX = view.x - event.rawX
                view.animate().cancel()
            }
            MotionEvent.ACTION_MOVE -> {
                view.x = event.rawX + dX
            }
            MotionEvent.ACTION_UP -> {
                val traveledDistance = view.x - initialX

                if (isAccept && traveledDistance > SWIPE_DISTANCE_REQUIRED) {
                    stopAll()
                    val intent = Intent(this, Active_Call_Screen::class.java)
                    intent.putExtra("PHONE_NUMBER", callerId)
                    startActivity(intent)
                    finish()
                } else if (!isAccept && traveledDistance < -SWIPE_DISTANCE_REQUIRED) {
                    stopAll()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    view.animate().x(initialX).translationY(0f).setDuration(300).start()
                }
            }
        }
        return true
    }

    private fun startCallEffects(btnAccept: View, btnReject: View) {
        // Vertical Bounce Runnable
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

        // Vibration
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val pattern = longArrayOf(0, 1000, 1000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createWaveform(pattern, 0))
        } else {
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