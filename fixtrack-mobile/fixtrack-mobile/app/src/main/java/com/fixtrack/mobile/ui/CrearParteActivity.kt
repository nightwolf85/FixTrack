package com.fixtrack.mobile.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fixtrack.mobile.R
import com.fixtrack.mobile.utils.FormValidator

class CrearParteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_parte) // Tu layout con EditTexts

        val etCliente = findViewById<EditText>(R.id.etClienteNombre)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcionAveria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarParte)

        btnGuardar.setOnClickListener {
            // Validamos cada campo utilizando la utilidad centralizada
            val esClienteValid = FormValidator.validateNotEmpty(etCliente, "El nombre del cliente es obligatorio")
            val esDescValid = FormValidator.validateNotEmpty(etDescripcion, "La descripción de la avería no puede estar vacía")

            // Si todas las validaciones son correctas, procedemos con el guardado o envío
            if (esClienteValid && esDescValid) {
                val cliente = etCliente.text.toString().trim()
                val descripcion = etDescripcion.text.toString().trim()

                // Lógica para guardar localmente en Room o enviar al backend
                Toast.makeText(this, "Datos válidos. Procesando parte...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, corrige los errores del formulario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}