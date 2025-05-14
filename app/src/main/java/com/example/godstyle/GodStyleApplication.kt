// app/src/main/java/com/example/godstyle/GodStyleApplication.kt
package com.example.godstyle

import android.app.Application
import androidx.room.Room
import com.example.godstyle.data.AppDatabase
import com.example.godstyle.repository.CitaRepository

class GodStyleApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set
    }

    lateinit var repository: CitaRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "cita_database"
        ).build()
        repository = CitaRepository(database.citaDao())
    }
}
