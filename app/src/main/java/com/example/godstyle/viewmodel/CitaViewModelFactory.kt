package com.example.godstyle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.godstyle.repository.CitaRepository

class CitaViewModelFactory(private val repo: CitaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CitaViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

