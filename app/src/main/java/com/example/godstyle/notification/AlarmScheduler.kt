package com.example.godstyle.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.godstyle.model.Cita
import java.util.*

object AlarmScheduler {
    private const val TAG = "AlarmScheduler"

    fun scheduleReminder(context: Context, cita: Cita) {
        // 1) Validar campos de fecha y hora
        val fecha = cita.fecha.trim()
        val hora = cita.hora.trim()
        if (fecha.isEmpty() || hora.isEmpty()) {
            Log.d(TAG, "Fecha u hora vacías para cita ${cita.id}, no se programa alarma.")
            return
        }

        // 2) Parsear fecha (dd/MM/yyyy)
        val partsFecha = fecha.split("/")
        if (partsFecha.size != 3) {
            Log.e(TAG, "Formato de fecha inválido: '$fecha'. Se espera dd/MM/yyyy")
            return
        }
        val dia = partsFecha[0].toIntOrNull()
        val mes = partsFecha[1].toIntOrNull()?.minus(1)
        val año = partsFecha[2].toIntOrNull()
        if (dia == null || mes == null || año == null) {
            Log.e(TAG, "Valores de fecha no numéricos: $partsFecha")
            return
        }

        // 3) Parsear hora (HH:mm)
        val partsHora = hora.split(":")
        if (partsHora.size != 2) {
            Log.e(TAG, "Formato de hora inválido: '$hora'. Se espera HH:mm")
            return
        }
        val horaDia = partsHora[0].toIntOrNull()
        val minuto = partsHora[1].toIntOrNull()
        if (horaDia == null || minuto == null) {
            Log.e(TAG, "Valores de hora no numéricos: $partsHora")
            return
        }

        // 4) Construir Calendar para trigger
        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, año)
            set(Calendar.MONTH, mes)
            set(Calendar.DAY_OF_MONTH, dia)
            set(Calendar.HOUR_OF_DAY, horaDia)
            set(Calendar.MINUTE, minuto)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val triggerTime = cal.timeInMillis

        // 5) Calcular 30 minutos antes
        val offset = 30 * 60_000L
        val reminderTime = triggerTime - offset
        val now = System.currentTimeMillis()

        // 6) Si la cita está dentro de los próximos 30 minutos o ya pasó -> notificar ya
        if (reminderTime <= now) {
            Log.d(TAG, "Cita ${cita.id} en menos de 30 minutos, notificando inmediatamente.")
            val title = "Recordatorio: Cita próxima"
            val message = "Tienes la cita con ${cita.cliente} para ${cita.servicio} a las $hora"
            NotificationHelper(context).showNotification(cita.id, title, message)
            return
        }

        // 7) Programar alarma exacta
        Log.d(TAG, "Programando alarma para cita ${cita.id} a las ${Date(reminderTime)}")
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("cita_id", cita.id)
            putExtra("cliente", cita.cliente)
            putExtra("servicio", cita.servicio)
            putExtra("fechaHora", "$fecha $hora")
        }
        val pi = PendingIntent.getBroadcast(
            context,
            cita.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        try {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, reminderTime, pi)
        } catch (e: SecurityException) {
            Log.w(TAG, "Exact alarm no permitido, usando set() como fallback", e)
            am.set(AlarmManager.RTC_WAKEUP, reminderTime, pi)
        }
    }

    fun cancelReminder(context: Context, citaId: Int) {
        Log.d(TAG, "Cancelando alarma para cita $citaId")
        val intent = Intent(context, AlarmReceiver::class.java)
        val pi = PendingIntent.getBroadcast(
            context,
            citaId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.cancel(pi)
    }
}
