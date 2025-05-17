// app/src/main/java/com/example/godstyle/notification/AlarmScheduler.kt
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
        val fecha = cita.fecha.trim()
        val hora  = cita.hora.trim()
        if (fecha.isEmpty() || hora.isEmpty()) {
            Log.d(TAG, "Fecha u hora vacías para cita ${cita.id}, no se programa alarma.")
            return
        }
        // Parseo de fecha y hora:
        val (d, m, y) = fecha.split("/").mapNotNull { it.toIntOrNull() }.let {
            if (it.size != 3) { Log.e(TAG, "Fecha inválida: $fecha"); return }
            Triple(it[0], it[1] - 1, it[2])
        }
        val (hh, mm) = hora.split(":").mapNotNull { it.toIntOrNull() }.let {
            if (it.size != 2) { Log.e(TAG, "Hora inválida: $hora"); return }
            Pair(it[0], it[1])
        }

        val cal = Calendar.getInstance().apply {
            set(Calendar.YEAR, y)
            set(Calendar.MONTH, m)
            set(Calendar.DAY_OF_MONTH, d)
            set(Calendar.HOUR_OF_DAY, hh)
            set(Calendar.MINUTE, mm)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val triggerTime = cal.timeInMillis
        val now = System.currentTimeMillis()

        // 15 minutos antes
        val reminderTime = triggerTime - 15 * 60_000L

        // Si ya pasó o está en menos de 15' → notificar ya
        if (reminderTime <= now) {
            Log.d(TAG, "Cita ${cita.id} en <15', notificando ahora.")
            NotificationHelper(context)
                .showNotification(
                    cita.id,
                    "Recordatorio: Cita próxima",
                    "Cita con ${cita.cliente} a las ${cita.hora}"
                )
            return
        }

        // Programar alarma exacta
        Log.d(TAG, "Programando recordatorio para cita ${cita.id} a ${Date(reminderTime)}")
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
        } catch (sec: SecurityException) {
            Log.w(TAG, "No permitido exact alarm, usando set()", sec)
            am.set(AlarmManager.RTC_WAKEUP, reminderTime, pi)
        }
    }

    fun cancelReminder(context: Context, citaId: Int) {
        Log.d(TAG, "Cancelando recordatorio cita $citaId")
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
