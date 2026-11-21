package com.fixtrack.mobile.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fixtrack.mobile.R
import com.fixtrack.mobile.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CerrarParteActivity : AppCompatActivity() {

    private lateinit var firmaCanvas: FirmaCanvasView
    private var parteId: Long = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cerrar_parte)

        firmaCanvas = findViewById(R.id.firmaCanvas)
        val btnLimpiar = findViewById<Button>(R.id.btnLimpiarFirma)
        val btnFinalizar = findViewById<Button>(R.id.btnGuardarYCerrar)

        btnLimpiar.setOnClickListener {
            firmaCanvas.limpiarFirma()
        }

        btnFinalizar.setOnClickListener {
            // Obtenemos la firma codificada en Base64 desde el canvas táctil
            val firmaBase64 = firmaCanvas.obtenerFirmaBase64()

            // Preparamos el cuerpo que espera tu ApiService existente
            val bodyMap = mapOf(
                "estado" to "FINALIZADO",
                "firmaCliente" to firmaBase64
            )

            enviarCierreServidor(parteId, bodyMap)
        }
    }

    private fun enviarCierreServidor(id: Long, body: Map<String, String?>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Utiliza tu método existente en RetrofitClient / ApiService
                val response = ApiClient.apiService.cerrarParte(id, body)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CerrarParteActivity, "Parte cerrado y firmado con éxito", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@CerrarParteActivity, "Error al cerrar el parte en el servidor", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CerrarParteActivity, "Sin conexión. Guardado en caché local.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}