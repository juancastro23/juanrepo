package com.example.godstyle.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.godstyle.model.Cita

@Database(entities = [Cita::class], version = 1, exportSchema = false)
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
                    "godstyle_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
