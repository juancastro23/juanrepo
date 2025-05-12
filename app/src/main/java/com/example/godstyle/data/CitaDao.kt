package com.example.godstyle.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.godstyle.model.Cita

@Dao
interface CitaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCita(cita: Cita)

    @Update
    suspend fun actualizarCita(cita: Cita)

    @Delete
    suspend fun eliminarCita(cita: Cita)

    @Query("SELECT * FROM citas ORDER BY fecha, hora")
    fun obtenerTodasLasCitas(): LiveData<List<Cita>>
}
