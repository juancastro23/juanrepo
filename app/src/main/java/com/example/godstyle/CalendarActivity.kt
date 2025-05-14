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
import kotlinx.coroutines.Dispatchers
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
    private var selectedDate: String = dateFormat.format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerViews
        val adapterHoy = CitaAdapter(
            onDeleteClick = ::confirmarYEliminar,
            onEditClick   = ::irAEditar
        )
        binding.rvCitasDelDia.layoutManager = LinearLayoutManager(this)
        binding.rvCitasDelDia.adapter = adapterHoy

        val adapterHistorial = CitaAdapter(
            onDeleteClick = ::confirmarYEliminar,
            onEditClick   = ::irAEditar
        )
        binding.rvHistorial.layoutManager = LinearLayoutManager(this)
        binding.rvHistorial.adapter = adapterHistorial

        val uid = auth.currentUser?.uid ?: return

        fun cargarPara(fecha: String) {
            selectedDate = fecha
            // 1) Citas del día
            viewModel.obtenerCitasPorFechaUsuario(uid, fecha)
                .observe(this) { listaHoy ->
                    adapterHoy.submitList(listaHoy)
                }
            // 2) Historial: todas las citas != fecha y fecha < hoy
            viewModel.obtenerCitasPorUsuario(uid)
                .observe(this) { todas ->
                    val historial = todas.filter { cita ->
                        val citaDate = dateFormat.parse(cita.fecha)!!
                        val selDate  = dateFormat.parse(fecha)!!
                        citaDate.before(selDate)
                    }
                    adapterHistorial.submitList(historial)
                }
        }

        // carga inicial
        cargarPara(selectedDate)

        binding.calendarView.setOnDateChangeListener { _, year, month, day ->
            val cal = Calendar.getInstance().apply { set(year, month, day) }
            cargarPara(dateFormat.format(cal.time))
        }
    }

    private fun confirmarYEliminar(cita: Cita) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar cita")
            .setMessage("¿Estás seguro de que quieres eliminar esta cita?")
            .setPositiveButton("Sí") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.eliminar(cita)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun irAEditar(cita: Cita) {
        startActivity(Intent(this, EditarCitaActivity::class.java).apply {
            putExtra("cita_id", cita.id)
        })
    }
}
