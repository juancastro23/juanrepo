// app/src/main/java/com/example/godstyle/EditarCitaActivity.kt
package com.example.godstyle

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.godstyle.databinding.ActivityEditarCitaBinding
import com.example.godstyle.model.Cita
import com.example.godstyle.notification.AlarmScheduler
import com.example.godstyle.viewmodel.CitaViewModel
import com.example.godstyle.viewmodel.CitaViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class EditarCitaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarCitaBinding
    private val citaViewModel: CitaViewModel by viewModels {
        CitaViewModelFactory((application as GodStyleApplication).repository)
    }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private var citaOriginal: Cita? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarCitaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val citaId = intent.getIntExtra("cita_id", -1)
        if (citaId == -1) {
            Toast.makeText(this, "ID de cita no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Cargar la cita y rellenar los campos
        citaViewModel.obtenerCitaPorId(citaId).observe(this) { cita ->
            citaOriginal = cita
            binding.editNombreCliente.setText(cita.cliente)
            binding.editServicio.setText(cita.servicio)
            binding.editFecha.setText(cita.fecha)
            binding.editHora.setText(cita.hora)
            binding.editNotas.setText(cita.notas)
        }

        binding.btnGuardar.setOnClickListener {
            val cliente  = binding.editNombreCliente.text.toString().trim()
            val servicio = binding.editServicio.text.toString().trim()
            val fecha    = binding.editFecha.text.toString().trim()
            val hora     = binding.editHora.text.toString().trim()
            val notas    = binding.editNotas.text.toString().trim()
            val userId   = auth.currentUser?.uid
            val original = citaOriginal

            if (original == null || userId == null) {
                Toast.makeText(this, "Error al actualizar cita", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (cliente.isEmpty() || servicio.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val citaActualizada = original.copy(
                userId   = userId,
                cliente  = cliente,
                servicio = servicio,
                fecha    = fecha,
                hora     = hora,
                notas    = notas
            )

            lifecycleScope.launch {
                // Cancelar y reprogramar la notificación interna
                AlarmScheduler.cancelReminder(this@EditarCitaActivity, citaActualizada.id)
                citaViewModel.actualizar(citaActualizada)
                AlarmScheduler.scheduleReminder(this@EditarCitaActivity, citaActualizada)
                Toast.makeText(this@EditarCitaActivity, "Cita actualizada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
