package com.fixtrack.mobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.fixtrack.mobile.MainActivity
import com.fixtrack.mobile.R
import com.fixtrack.mobile.utils.FormValidator

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etLoginEmail)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val emailValido = FormValidator.validateEmail(etEmail)
            val passValida = FormValidator.validateNotEmpty(etPassword, "Introduce tu contraseña")

            if (emailValido && passValida) {
                // Aquí irá la llamada a ApiService.login(...)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}