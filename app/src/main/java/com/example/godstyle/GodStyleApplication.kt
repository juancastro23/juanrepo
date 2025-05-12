package com.example.godstyle

import android.app.Application
import com.example.godstyle.data.AppDatabase
import com.example.godstyle.repository.CitaRepository

class GodStyleApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { CitaRepository(database.citaDao()) }
}
