package com.fixtrack.mobile.utils

import android.widget.EditText
import android.util.Patterns

object FormValidator {

    /**
     * Valida que un campo no esté vacío y cumpla opcionalmente con un patrón (ej. Email).
     */
    fun validateNotEmpty(editText: EditText, errorMessage: String): Boolean {
        val text = editText.text.toString().trim()
        return if (text.isEmpty()) {
            editText.error = errorMessage
            false
        } else {
            editText.error = null
            true
        }
    }

    fun validateEmail(editText: EditText): Boolean {
        val email = editText.text.toString().trim()
        return if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.error = "Introduce un correo electrónico válido"
            false
        } else {
            editText.error = null
            true
        }
    }

    fun validatePositiveNumber(editText: EditText, errorMessage: String): Boolean {
        val value = editText.text.toString().trim().toDoubleOrNull()
        return if (value == null || value <= 0.0) {
            editText.error = errorMessage
            false
        } else {
            editText.error = null
            true
        }
    }
}