package com.example.justcall

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.*
import androidx.activity.OnBackPressedCallback // Naya Import
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Active_Call_Screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_call_screen)

        val chronometer = findViewById<Chronometer>(R.id.callTimer)
        val btnMute = findViewById<ImageButton>(R.id.btnMute)
        val btnSpeaker = findViewById<ImageButton>(R.id.btnSpeaker)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                endCallAndGoToDialer()
            }
        })


        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()


        var isMuted = false
        btnMute.setOnClickListener {
            isMuted = !isMuted
            btnMute.setImageResource(if (isMuted) R.drawable.ic_mic_off else R.drawable.ic_mic_on)
        }

        var isSpeakerOn = false
        btnSpeaker.setOnClickListener {
            isSpeakerOn = !isSpeakerOn
            btnSpeaker.setImageResource(if (isSpeakerOn) R.drawable.ic_volume_up else R.drawable.ic_volume_off)
        }

        // End Call Button
        findViewById<FloatingActionButton>(R.id.btnEndActiveCall).setOnClickListener {
            endCallAndGoToDialer()
        }
    }
    private fun endCallAndGoToDialer() {
        findViewById<Chronometer>(R.id.callTimer).stop()

        val intent = Intent(this, MainActivity::class.java)
        // Ye flags saara stack clear kar denge
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)

        finish()
    }
}