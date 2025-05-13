package com.example.godstyle.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.godstyle.model.Cita

/**
 * Base de datos de Room para las citas.
 * Se incrementa la versión tras cambios en el esquema y
 * se usa fallbackToDestructiveMigration() para recrear la BD
 * automáticamente durante el desarrollo.
 */
@Database(entities = [Cita::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun citaDao(): CitaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "citas_db"
                )
                    // Permite destruir y recrear la BD si cambia el esquema
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
