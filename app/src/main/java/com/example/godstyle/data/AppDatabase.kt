// app/src/main/java/com/example/godstyle/data/AppDatabase.kt
package com.example.godstyle.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.godstyle.model.Cita

@Database(entities = [Cita::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun citaDao(): CitaDao
}
