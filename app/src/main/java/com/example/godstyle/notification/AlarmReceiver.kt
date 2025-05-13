package com.example.godstyle.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id      = intent.getIntExtra("cita_id", -1)
        val cliente = intent.getStringExtra("cliente") ?: ""
        val serv    = intent.getStringExtra("servicio") ?: ""
        val cuando  = intent.getStringExtra("fechaHora") ?: ""

        val title   = "Recordatorio: Cita próxima"
        val message = "Cita con $cliente ($serv) a las $cuando"

        NotificationHelper(context).showNotification(id, title, message)
    }
}
