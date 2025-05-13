package com.example.godstyle.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

class NotificationHelper(private val context: Context) {

    companion object {
        private const val CHANNEL_ID   = "citas_channel"
        private const val CHANNEL_NAME = "Recordatorios de Citas"
        private const val CHANNEL_DESC = "Alertas de citas próximas"
    }

    fun showNotification(id: Int, title: String, message: String) {
        val mgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear canal si no existe
        if (mgr.getNotificationChannel(CHANNEL_ID) == null) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply { description = CHANNEL_DESC }
            mgr.createNotificationChannel(channel)
        }

        // Construir notificación
        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()

        mgr.notify(id, notif)
    }
}
