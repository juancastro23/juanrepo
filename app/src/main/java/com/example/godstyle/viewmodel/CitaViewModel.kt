package com.example.godstyle.viewmodel

import androidx.lifecycle.*
import com.example.godstyle.model.Cita
import com.example.godstyle.repository.CitaRepository
import kotlinx.coroutines.launch

class CitaViewModel(private val repository: CitaRepository) : ViewModel() {
    val todasLasCitas = repository.todasLasCitas

    fun insertar(cita: Cita) = viewModelScope.launch {
        repository.insertar(cita)
    }

    fun actualizar(cita: Cita) = viewModelScope.launch {
        repository.actualizar(cita)
    }

    fun eliminar(cita: Cita) = viewModelScope.launch {
        repository.eliminar(cita)
    }
}

class CitaViewModelFactory(private val repository: CitaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CitaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
