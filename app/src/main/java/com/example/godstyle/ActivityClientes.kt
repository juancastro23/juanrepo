package com.example.godstyle

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.godstyle.adapter.CitaAdapter
import com.example.godstyle.databinding.ActivityClientesBinding
import com.example.godstyle.model.Cita
import com.example.godstyle.viewmodel.CitaViewModel
import com.example.godstyle.viewmodel.CitaViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

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
            onEditClick = { cita ->
                // Navegar a edición
                startActivity(Intent(this, EditarCitaActivity::class.java).apply {
                    putExtra("cita_id", cita.id)
                })
            },
            onDeleteClick = { cita ->
                // Mostrar diálogo de confirmación
                AlertDialog.Builder(this)
                    .setTitle("Eliminar cita")
                    .setMessage("¿Estás seguro de que quieres eliminar esta cita?")
                    .setPositiveButton("Sí") { _, _ ->
                        lifecycleScope.launch {
                            citaViewModel.eliminar(cita)
                        }
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        )
        binding.recyclerCitas.layoutManager = LinearLayoutManager(this)
        binding.recyclerCitas.adapter = adapter

        val userId = FirebaseAuth.getInstance().currentUser?.uid
            ?: run {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return
            }

        citaViewModel.obtenerCitasPorUsuario(userId)
            .observe(this) { lista ->
                adapter.submitList(lista)
            }
    }
}
