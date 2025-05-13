package com.example.godstyle

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.godstyle.databinding.ActivityAnadirCitaBinding
import com.example.godstyle.model.Cita
import com.example.godstyle.notification.AlarmScheduler
import com.example.godstyle.viewmodel.CitaViewModel
import com.example.godstyle.viewmodel.CitaViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class AnadirCitaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnadirCitaBinding
    private val citaViewModel: CitaViewModel by viewModels {
        CitaViewModelFactory((application as GodStyleApplication).repository)
    }
    private val auth by lazy { FirebaseAuth.getInstance() }

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

            if (userId == null) {
                Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (cliente.isEmpty() || servicio.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cita = Cita(
                id       = 0,
                userId   = userId,
                cliente  = cliente,
                servicio = servicio,
                fecha    = fecha,
                hora     = hora,
                notas    = notas
            )

            citaViewModel.insertar(cita)
            AlarmScheduler.scheduleReminder(this, cita)
            Toast.makeText(this, "Cita guardada", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, ActivityClientes::class.java))
            finish()
        }
    }
}