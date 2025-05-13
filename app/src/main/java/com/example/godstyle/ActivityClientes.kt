package com.example.godstyle

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.godstyle.databinding.ActivityClientesBinding
import com.example.godstyle.model.Cita
import com.example.godstyle.viewmodel.CitaViewModel
import com.example.godstyle.viewmodel.CitaViewModelFactory
import com.example.godstyle.adapter.CitaAdapter

class ActivityClientes : AppCompatActivity() {

    private lateinit var binding: ActivityClientesBinding
    private val citaViewModel: CitaViewModel by viewModels {
        CitaViewModelFactory((application as GodStyleApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CitaAdapter(
            onDeleteClick = { cita -> confirmarEliminacion(cita) },
            onEditClick = { /* No hace nada por ahora */ }
        )


        binding.recyclerCitas.layoutManager = LinearLayoutManager(this)
        binding.recyclerCitas.adapter = adapter

        citaViewModel.todasLasCitas.observe(this) { citas ->
            adapter.submitList(citas)
        }
    }

    private fun confirmarEliminacion(cita: Cita) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar cita")
            .setMessage("¿Estás seguro de que quieres eliminar esta cita?")
            .setPositiveButton("Sí") { _, _ ->
                // 1) Eliminar la cita de la base de datos
                citaViewModel.eliminar(cita)
                // 2) Navegar al menú principal
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                // 3) Cerrar esta Activity para que no quede en el back stack
                finish()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

}

