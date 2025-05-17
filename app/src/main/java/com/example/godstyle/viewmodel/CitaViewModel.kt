// app/src/main/java/com/example/godstyle/viewmodel/CitaViewModel.kt
package com.example.godstyle.viewmodel

import androidx.lifecycle.*
import com.example.godstyle.model.Cita
import com.example.godstyle.repository.CitaRepository
import kotlinx.coroutines.launch

class CitaViewModel(private val repo: CitaRepository) : ViewModel() {
    fun obtenerCitasPorUsuario(userId: String): LiveData<List<Cita>> =
        repo.obtenerCitasPorUsuario(userId).asLiveData()

    fun obtenerCitasPorFechaUsuario(userId: String, fecha: String): LiveData<List<Cita>> =
        repo.obtenerCitasPorFechaUsuario(userId, fecha).asLiveData()

    fun obtenerCitaPorId(id: Int): LiveData<Cita> =
        repo.obtenerCitaPorId(id).asLiveData()

    fun insertar(cita: Cita) = viewModelScope.launch { repo.insertar(cita) }
    fun actualizar(cita: Cita) = viewModelScope.launch { repo.actualizar(cita) }
    fun eliminar(cita: Cita)   = viewModelScope.launch { repo.eliminar(cita) }

    suspend fun existeCita(fecha: String, hora: String): Boolean =
        repo.existeCita(fecha, hora)
}
