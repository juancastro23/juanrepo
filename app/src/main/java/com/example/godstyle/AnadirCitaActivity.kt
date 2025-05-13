package com.example.godstyle

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.godstyle.model.Cita
import com.example.godstyle.viewmodel.CitaViewModel
import com.example.godstyle.viewmodel.CitaViewModelFactory
import android.content.Intent


class AnadirCitaActivity : AppCompatActivity() {

    private val citaViewModel: CitaViewModel by viewModels {
        CitaViewModelFactory((application as GodStyleApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_cita)

        val cliente = findViewById<EditText>(R.id.inputCliente)
        val servicio = findViewById<EditText>(R.id.inputServicio)
        val fecha = findViewById<EditText>(R.id.inputFecha)
        val hora = findViewById<EditText>(R.id.inputHora)
        val notas = findViewById<EditText>(R.id.inputNotas)
        val guardar = findViewById<Button>(R.id.btnGuardar)

        val citaId = intent.getIntExtra("CITA_ID", -1)

        if (citaId != -1) {
            cliente.setText(intent.getStringExtra("CLIENTE"))
            servicio.setText(intent.getStringExtra("SERVICIO"))
            fecha.setText(intent.getStringExtra("FECHA"))
            hora.setText(intent.getStringExtra("HORA"))
            notas.setText(intent.getStringExtra("NOTAS"))
        }

        guardar.setOnClickListener {
            val cita = Cita(
                id = if (citaId != -1) citaId else 0,
                cliente = cliente.text.toString(),
                servicio = servicio.text.toString(),
                fecha = fecha.text.toString(),
                hora = hora.text.toString(),
                notas = notas.text.toString()
            )

            if (citaId == -1) {
                citaViewModel.insertar(cita)
                Toast.makeText(this, "Cita guardada", Toast.LENGTH_SHORT).show()
            } else {
                citaViewModel.actualizar(cita)
                Toast.makeText(this, "Cita actualizada", Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this, ActivityClientes::class.java)
            startActivity(intent)
            finish()
        }
    }
}
