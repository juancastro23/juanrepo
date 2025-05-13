package com.example.godstyle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.godstyle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón para añadir cita
        binding.btnAnadirCita.setOnClickListener {
            startActivity(Intent(this, AnadirCitaActivity::class.java))
        }

        // Botón para ver el listado de citas
        binding.btnVerCitas.setOnClickListener {
            startActivity(Intent(this, ActivityClientes::class.java))
        }
    }
}
