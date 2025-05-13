package com.example.godstyle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.godstyle.repository.CitaRepository

class CitaViewModelFactory(
    private val repository: CitaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CitaViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida: ${modelClass.name}")
    }
}
