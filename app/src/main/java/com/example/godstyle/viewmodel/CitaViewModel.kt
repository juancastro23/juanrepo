package com.example.godstyle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.godstyle.model.Cita
import com.example.godstyle.repository.CitaRepository
import kotlinx.coroutines.launch

class CitaViewModel(private val repository: CitaRepository) : ViewModel() {

    fun obtenerCitasUsuario(userId: String): LiveData<List<Cita>> =
        repository.obtenerCitasPorUsuario(userId)

    // <-- Método para editar:
    fun obtenerCitaPorId(id: Int): LiveData<Cita> =
        repository.obtenerPorId(id)

    fun insertar(cita: Cita) = viewModelScope.launch { repository.insertar(cita) }
    fun actualizar(cita: Cita) = viewModelScope.launch { repository.actualizar(cita) }
    fun eliminar(cita: Cita) = viewModelScope.launch { repository.eliminar(cita) }
}
