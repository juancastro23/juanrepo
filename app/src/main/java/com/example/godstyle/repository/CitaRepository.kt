// app/src/main/java/com/example/godstyle/repository/CitaRepository.kt
package com.example.godstyle.repository

import com.example.godstyle.data.CitaDao
import com.example.godstyle.model.Cita
import kotlinx.coroutines.flow.Flow

class CitaRepository(private val dao: CitaDao) {

    fun obtenerCitasPorUsuario(userId: String): Flow<List<Cita>> =
        dao.getCitasPorUsuario(userId)

    fun obtenerCitasPorFechaUsuario(userId: String, fecha: String): Flow<List<Cita>> =
        dao.getCitasPorFechaUsuario(userId, fecha)

    fun obtenerCitaPorId(id: Int): Flow<Cita> =
        dao.getCitaPorId(id)

    suspend fun insertar(cita: Cita) = dao.insertar(cita)
    suspend fun actualizar(cita: Cita) = dao.actualizar(cita)
    suspend fun eliminar(cita: Cita) = dao.eliminar(cita)
}
