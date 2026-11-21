package com.fixtrack.mobile.repository

import android.content.Context
import com.fixtrack.mobile.local.AppDatabase
import com.fixtrack.mobile.local.ParteEntity
import com.fixtrack.mobile.network.ApiClient
import com.fixtrack.mobile.network.ParteDto

class ParteRepository(context: Context) {
    private val parteDao = AppDatabase.getInstance(context).parteDao()
    private val apiService = ApiClient.apiService

    // Sincronizar los partes del técnico desde la API y guardarlos en Room
    suspend fun sincronizarPartesTecnico(tecnicoId: Long): List<ParteEntity> {
        return try {
            val response = apiService.getPartesPorTecnico(tecnicoId)
            if (response.isSuccessful && response.body() != null) {
                val listaDtos = response.body()!!

                // Mapeamos de ParteDto (Red) a ParteEntity (Room local)
                val listaEntities = listaDtos.map { dto ->
                    ParteEntity(
                        idParte = dto.idParte,
                        descripcionAveria = dto.descripcionAveria,
                        estado = dto.estado,
                        clienteNombre = dto.clienteNombre,
                        fecha = dto.fecha ?: "",
                        sincronizado = true
                    )
                }

                // Guardamos en la base de datos local SQLite (Room)
                parteDao.insertarTodos(listaEntities)
                listaEntities
            } else {
                // Si falla la red, devolvemos lo que hay en caché local
                parteDao.obtenerTodos()
            }
        } catch (e: Exception) {
            // Si hay excepción (sin conexión a internet), tiramos de Room directamente (Modo Offline)
            parteDao.obtenerTodos()
        }
    }
}