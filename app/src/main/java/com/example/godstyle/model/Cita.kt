// app/src/main/java/com/example/godstyle/model/Cita.kt
package com.example.godstyle.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cita_table")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val cliente: String,
    val servicio: String,
    val fecha: String,
    val hora: String,
    val notas: String?
)
