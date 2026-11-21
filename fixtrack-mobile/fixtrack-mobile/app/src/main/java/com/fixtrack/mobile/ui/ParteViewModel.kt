package com.fixtrack.mobile.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fixtrack.mobile.local.ParteEntity
import com.fixtrack.mobile.repository.ParteRepository
import kotlinx.coroutines.launch

class ParteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ParteRepository(application)

    // LiveData observable para la UI
    private val _partes = MutableLiveData<List<ParteEntity>>()
    val partes: LiveData<List<ParteEntity>> get() = _partes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Función para cargar los partes de un técnico específico
    fun cargarPartesTecnico(tecnicoId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            // Llama al repositorio (intenta sincronizar con red o tira de Room offline)
            val resultado = repository.sincronizarPartesTecnico(tecnicoId)
            _partes.value = resultado
            _isLoading.value = false
        }
    }
}