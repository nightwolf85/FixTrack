package com.fixtrack.mobile.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fixtrack.mobile.MainActivity
import com.fixtrack.mobile.R

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificamos si ya se mostró la guía anteriormente
        if (hasSeenOnboarding()) {
            navigateToMain()
            return
        }

        setContentView(R.layout.activity_onboarding)

        val btnFinishTutorial = findViewById<Button>(R.id.btnFinishTutorial)
        btnFinishTutorial.setOnClickListener {
            markOnboardingAsSeen()
            navigateToMain()
        }
    }

    private fun hasSeenOnboarding(): Boolean {
        val prefs = getSharedPreferences("FixTrackPrefs", Context.MODE_PRIVATE)
        return prefs.getBoolean("KEY_ONBOARDING_SHOWN", false)
    }

    private fun markOnboardingAsSeen() {
        val prefs = getSharedPreferences("FixTrackPrefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("KEY_ONBOARDING_SHOWN", true).apply()
    }

    private fun navigateToMain() {
        // Cambiar por tu Activity principal de la app (ej. LoginActivity o DashboardActivity)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}