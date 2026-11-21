package com.fixtrack.mobile.network

import retrofit2.Response
import retrofit2.http.*

data class ParteDto(
    val idParte: Long,
    val descripcionAveria: String,
    val estado: String,
    val clienteNombre: String,
    val fecha: String? = null
)

interface ApiService {
    // Obtener partes asignados a un técnico específico
    @GET("partes/tecnico/{id}")
    suspend fun getPartesPorTecnico(@Path("id") tecnicoId: Long): Response<List<ParteDto>>

    // Cerrar un parte de trabajo en el servidor
    @PUT("partes/{id}/cerrar")
    suspend fun cerrarParte(
        @Path("id") parteId: Long,
        @Body body: Map<String, String?>
    ): Response<Void>

    // Obtener todos los partes generales
    @GET("partes")
    suspend fun getTodosLosPartes(): Response<List<ParteDto>>
}