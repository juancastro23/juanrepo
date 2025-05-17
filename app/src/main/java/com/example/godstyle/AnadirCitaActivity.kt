// app/src/main/java/com/example/godstyle/AnadirCitaActivity.kt
package com.example.godstyle

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.godstyle.databinding.ActivityAnadirCitaBinding
import com.example.godstyle.model.Cita
import com.example.godstyle.notification.AlarmScheduler
import com.example.godstyle.viewmodel.CitaViewModel
import com.example.godstyle.viewmodel.CitaViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AnadirCitaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirCitaBinding
    private val citaViewModel: CitaViewModel by viewModels {
        CitaViewModelFactory((application as GodStyleApplication).repository)
    }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnadirCitaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {
            val cliente  = binding.inputCliente.text.toString().trim()
            val servicio = binding.inputServicio.text.toString().trim()
            val fecha    = binding.inputFecha.text.toString().trim()
            val hora     = binding.inputHora.text.toString().trim()
            val notas    = binding.inputNotas.text.toString().trim()
            val userId   = auth.currentUser?.uid

            // Validaciones básicas...
            if (userId == null) {
                Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (cliente.isEmpty() || servicio.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 0) No permitir fechas pasadas
            val fechaHoraStr = "$fecha $hora"
            val dtCita = try {
                dateTimeFormat.parse(fechaHoraStr)!!
            } catch (e: Exception) {
                Toast.makeText(this, "Formato de fecha/hora inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (dtCita.before(Date())) {
                Toast.makeText(this, "No puedes agendar citas en fechas pasadas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // (Aquí podrías mantener tus validaciones de días laborales y horario...)

            val cita = Cita(0, userId, cliente, servicio, fecha, hora, notas)

            lifecycleScope.launch {
                // Comprobar solapamiento...
                val hay = citaViewModel.existeCita(fecha, hora)
                if (hay) {
                    Toast.makeText(this@AnadirCitaActivity, "Ya hay una cita a esa hora", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                // Guardar y programar recordatorio
                citaViewModel.insertar(cita)
                AlarmScheduler.scheduleReminder(this@AnadirCitaActivity, cita)
                Toast.makeText(this@AnadirCitaActivity, "Cita guardada", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@AnadirCitaActivity, ActivityClientes::class.java))
                finish()
            }
        }
    }
}
