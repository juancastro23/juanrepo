package com.example.godstyle.repository

import com.example.godstyle.data.CitaDao
import com.example.godstyle.model.Cita

class CitaRepository(private val citaDao: CitaDao) {
    val todasLasCitas = citaDao.obtenerTodasLasCitas()

    suspend fun insertar(cita: Cita) = citaDao.insertarCita(cita)

    suspend fun actualizar(cita: Cita) = citaDao.actualizarCita(cita)

    suspend fun eliminar(cita: Cita) = citaDao.eliminarCita(cita)
}
