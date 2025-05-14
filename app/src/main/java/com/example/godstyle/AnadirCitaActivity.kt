package com.example.godstyle

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    companion object {
        private const val CALENDAR_PERM_REQUEST = 1001
    }

    private lateinit var binding: ActivityAnadirCitaBinding
    private val citaViewModel: CitaViewModel by viewModels {
        CitaViewModelFactory((application as GodStyleApplication).repository)
    }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val isoFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnadirCitaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pedimos permiso en tiempo de ejecución si hace falta
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR),
                CALENDAR_PERM_REQUEST
            )
        }

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

            lifecycleScope.launch {
                // 1) Inserta en la BD
                citaViewModel.insertar(cita)

                // 2) Programa tu recordatorio interno
                AlarmScheduler.scheduleReminder(this@AnadirCitaActivity, cita)

                // 3) Inserta un evento en el Calendario de Android
                insertarEnCalendario(cita)

                Toast.makeText(
                    this@AnadirCitaActivity,
                    "Cita guardada y añadida al calendario",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this@AnadirCitaActivity, ActivityClientes::class.java))
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == CALENDAR_PERM_REQUEST) {
            if (grantResults.any { it != PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(
                    this,
                    "Sin permiso de calendario no se pueden añadir eventos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun insertarEnCalendario(cita: Cita) {
        // convierte fecha y hora a milisegundos
        val fechaHora = "${cita.fecha} ${cita.hora}"
        val dt = isoFormat.parse(fechaHora) ?: return
        val beginMillis = dt.time
        val endMillis = beginMillis + 60 * 60 * 1000  // duración 1 hora

        // 1) obtiene ID de calendario primario
        val cursor = contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            arrayOf(CalendarContract.Calendars._ID),
            "${CalendarContract.Calendars.VISIBLE}=1 AND ${CalendarContract.Calendars.IS_PRIMARY}=1",
            null, null
        )
        val calId = cursor?.use {
            if (it.moveToFirst()) it.getLong(0) else null
        } ?: return

        // 2) construye valores y realiza insert
        val values = ContentValues().apply {
            put(CalendarContract.Events.CALENDAR_ID, calId)
            put(CalendarContract.Events.TITLE, "Cita con ${cita.cliente}")
            put(CalendarContract.Events.DESCRIPTION, "${cita.servicio}\n${cita.notas}")
            put(CalendarContract.Events.DTSTART, beginMillis)
            put(CalendarContract.Events.DTEND, endMillis)
            put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        }
        contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
    }
}
