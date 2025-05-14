package com.example.godstyle.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.godstyle.model.Cita
import com.example.godstyle.data.CitaDao

@Database(entities = [Cita::class], version = 1, exportSchema = false)
abstract class CitaDatabase : RoomDatabase() {
    abstract fun citaDao(): CitaDao

    companion object {
        @Volatile private var INSTANCE: CitaDatabase? = null

        fun getDatabase(context: Context): CitaDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CitaDatabase::class.java,
                    "cita_database"
                ).build().also { INSTANCE = it }
            }
    }
}
