package com.example.godstyle

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.example.godstyle.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import android.app.AlarmManager
import android.content.Context

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) Mostrar nombre/email de usuario
        val user = auth.currentUser
        val name = user?.displayName
            ?: user?.email?.substringBefore("@")
            ?: "Usuario"
        binding.tvWelcome.text = "Bienvenido a su perfil, $name"

        // 2) Comprobar permiso de exact alarms (Android 12+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!am.canScheduleExactAlarms()) {
                startActivity(
                    Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                        data = Uri.parse("package:$packageName")
                    }
                )
            }
        }

        // 3) Botones de navegación
        binding.btnAnadirCita.setOnClickListener {
            startActivity(Intent(this, AnadirCitaActivity::class.java))
        }
        binding.btnVerCitas.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }
    }
}