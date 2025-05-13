package com.example.godstyle

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.godstyle.databinding.ActivityEditarCitaBinding
import com.example.godstyle.model.Cita
import com.example.godstyle.viewmodel.CitaViewModel
import com.example.godstyle.viewmodel.CitaViewModelFactory

class EditarCitaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarCitaBinding
    private val citaViewModel: CitaViewModel by viewModels {
        CitaViewModelFactory((application as GodStyleApplication).repository)
    }

    private var citaId: Int = -1
    private var citaOriginal: Cita? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarCitaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperamos el ID
        citaId = intent.getIntExtra("cita_id", -1)
        if (citaId == -1) {
            Toast.makeText(this, "ID de cita no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Observamos la cita por su ID
        citaViewModel.obtenerCitaPorId(citaId).observe(this) { cita ->
            if (cita != null) {
                citaOriginal = cita
                binding.editNombreCliente.setText(cita.cliente)
                binding.editServicio.setText(cita.servicio)
                binding.editFecha.setText(cita.fecha)
                binding.editHora.setText(cita.hora)
                // Si tienes el campo notas en el formulario, descomenta:
                // binding.editNotas.setText(cita.notas)
            }
        }

        binding.btnGuardar.setOnClickListener {
            val nombre = binding.editNombreCliente.text.toString()
            val servicio = binding.editServicio.text.toString()
            val fecha = binding.editFecha.text.toString()
            val hora = binding.editHora.text.toString()
            // Si tienes notas en el formulario, úsalo aquí:
            // val notas = binding.editNotas.text.toString()

            // Creamos la copia actualizada;
            // dejamos las notas originales para no perderlas
            val citaActualizada = citaOriginal?.copy(
                cliente = nombre,
                servicio = servicio,
                fecha = fecha,
                hora = hora,
                notas = citaOriginal?.notas ?: ""
            )

            if (citaActualizada != null) {
                citaViewModel.actualizar(citaActualizada)
                Toast.makeText(this, "Cita actualizada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
