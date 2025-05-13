package com.example.godstyle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val emailField    = findViewById<EditText>(R.id.emailRegisterEditText)
        val passField     = findViewById<EditText>(R.id.passwordRegisterEditText)
        val confirmField  = findViewById<EditText>(R.id.confirmPasswordEditText)
        val registerBtn   = findViewById<Button>(R.id.registerButton)
        val loginRedirect = findViewById<Button>(R.id.loginRedirectButton)

        registerBtn.setOnClickListener {
            val email = emailField.text.toString().trim()
            val pass  = passField.text.toString()
            val pass2 = confirmField.text.toString()

            if (email.isEmpty() || pass.length < 6 || pass != pass2) {
                Toast.makeText(this, "Revisa los datos: email, contraseña y confirmación.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                    // Tras registro, vamos a MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }

        loginRedirect.setOnClickListener {
            // Volvemos a LoginActivity
            finish()
        }
    }
}
