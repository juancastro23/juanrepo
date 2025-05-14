// app/src/main/java/com/example/godstyle/data/CitaDao.kt
package com.example.godstyle.data

import androidx.room.*
import com.example.godstyle.model.Cita
import kotlinx.coroutines.flow.Flow

@Dao
interface CitaDao {
    @Query("SELECT * FROM cita_table")
    fun getTodasLasCitas(): Flow<List<Cita>>

    @Query("SELECT * FROM cita_table WHERE userId = :userId")
    fun getCitasPorUsuario(userId: String): Flow<List<Cita>>

    @Query("SELECT * FROM cita_table WHERE id = :id")
    fun getCitaPorId(id: Int): Flow<Cita>

    @Query("SELECT * FROM cita_table WHERE userId = :userId AND fecha = :fecha")
    fun getCitasPorFechaUsuario(userId: String, fecha: String): Flow<List<Cita>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(cita: Cita)

    @Update
    suspend fun actualizar(cita: Cita)

    @Delete
    suspend fun eliminar(cita: Cita)
}
