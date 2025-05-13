package com.example.godstyle.repository

import androidx.lifecycle.LiveData
import com.example.godstyle.data.CitaDao
import com.example.godstyle.model.Cita

class CitaRepository(private val citaDao: CitaDao) {
    fun obtenerCitasPorUsuario(userId: String): LiveData<List<Cita>> =
        citaDao.obtenerCitasPorUsuario(userId)

    fun obtenerPorId(id: Int): LiveData<Cita> =
        citaDao.obtenerCitaPorId(id)

    suspend fun insertar(cita: Cita) = citaDao.insertarCita(cita)
    suspend fun actualizar(cita: Cita) = citaDao.actualizarCita(cita)
    suspend fun eliminar(cita: Cita) = citaDao.eliminarCita(cita)
}
