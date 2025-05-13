package com.example.godstyle

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.godstyle.adapter.CitaAdapter
import com.example.godstyle.databinding.ActivityClientesBinding
import com.example.godstyle.model.Cita
import com.example.godstyle.viewmodel.CitaViewModel
import com.example.godstyle.viewmodel.CitaViewModelFactory

class ActivityClientes : AppCompatActivity() {

    private lateinit var binding: ActivityClientesBinding
    private val citaViewModel: CitaViewModel by viewModels {
        CitaViewModelFactory((application as GodStyleApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) Creamos el adapter, pasando lambdas para eliminar y para editar
        val adapter = CitaAdapter(
            onDeleteClick = { cita -> confirmarEliminacion(cita) },
            onEditClick = { cita ->
                // 2) Al pulsar editar, lanzamos EditarCitaActivity con el ID
                val intent = Intent(this, EditarCitaActivity::class.java).apply {
                    putExtra("cita_id", cita.id)
                }
                startActivity(intent)
            }
        )

        // 3) Configuramos el RecyclerView
        binding.recyclerCitas.layoutManager = LinearLayoutManager(this)
        binding.recyclerCitas.adapter = adapter

        // 4) Observamos la lista y la pasamos al adapter
        citaViewModel.todasLasCitas.observe(this) { citas ->
            adapter.submitList(citas)
        }
    }

    private fun confirmarEliminacion(cita: Cita) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar cita")
            .setMessage("¿Estás seguro de que quieres eliminar esta cita?")
            .setPositiveButton("Sí") { _, _ ->
                citaViewModel.eliminar(cita)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
