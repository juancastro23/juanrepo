package com.example.godstyle.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val cliente: String,
    val servicio: String,
    val fecha: String,
    val hora: String,
    val notas: String
)
