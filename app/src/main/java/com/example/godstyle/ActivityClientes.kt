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
import com.google.firebase.auth.FirebaseAuth

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
            onEditClick = { cita ->
                val intent = Intent(this, EditarCitaActivity::class.java).apply {
                    putExtra("cita_id", cita.id)
                }
                startActivity(intent)
            }
        )
        binding.recyclerCitas.layoutManager = LinearLayoutManager(this)
        binding.recyclerCitas.adapter = adapter

        // --- FILTRAR POR USUARIO LOGUEADO ---
        val userId = FirebaseAuth.getInstance().currentUser?.uid
            ?: run {
                // si no hay usuario, volver a login
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return
            }

        // Observar solo las citas de este usuario
        citaViewModel.obtenerCitasUsuario(userId).observe(this) { citas ->
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
