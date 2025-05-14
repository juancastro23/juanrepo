package com.example.godstyle

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.godstyle.adapter.CitaAdapter
import com.example.godstyle.databinding.ActivityCalendarBinding
import com.example.godstyle.model.Cita
import com.example.godstyle.viewmodel.CitaViewModel
import com.example.godstyle.viewmodel.CitaViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val viewModel: CitaViewModel by viewModels {
        CitaViewModelFactory((application as GodStyleApplication).repository)
    }
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CitaAdapter(
            onDeleteClick = { cita ->
                // mostramos confirmación
                AlertDialog.Builder(this)
                    .setTitle("Eliminar cita")
                    .setMessage("¿Estás seguro de que quieres eliminar esta cita?")
                    .setPositiveButton("Sí") { _, _ ->
                        lifecycleScope.launch {
                            viewModel.eliminar(cita)
                        }
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            },
            onEditClick = { cita ->
                startActivity(Intent(this, EditarCitaActivity::class.java).apply {
                    putExtra("cita_id", cita.id)
                })
            }
        )

        binding.rvCitasDelDia.layoutManager = LinearLayoutManager(this)
        binding.rvCitasDelDia.adapter = adapter

        val uid = auth.currentUser?.uid ?: return

        fun cargarCitasPara(fecha: String) {
            viewModel.obtenerCitasPorFechaUsuario(uid, fecha)
                .observe(this) { lista ->
                    adapter.submitList(lista)
                }
        }

        // carga inicial: hoy
        cargarCitasPara(dateFormat.format(Date()))

        binding.calendarView.setOnDateChangeListener { _, year, month, day ->
            val cal = Calendar.getInstance().apply { set(year, month, day) }
            cargarCitasPara(dateFormat.format(cal.time))
        }
    }
}
