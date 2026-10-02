package com.javeriana.javewheels.ui.components

import android.content.Context
import android.content.Intent
import com.javeriana.javewheels.entities.WheelPasajero

/** Menú nativo compartido por el detalle de reserva y el viaje en vivo. */
fun compartirViaje(context: Context, wheel: WheelPasajero) {
    val compartir = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Mi viaje en JaveWheels")
        putExtra(Intent.EXTRA_TEXT, """
            Mi viaje en JaveWheels
            ${wheel.origen} → ${wheel.destino}
            ${wheel.fecha} · Salida: ${wheel.hora}
            Conductor: ${wheel.conductor}
            Vehículo: ${wheel.vehiculo}
            Recogida: ${wheel.puntoRecogida}
            Hora estimada de recogida: ${wheel.horaRecogida}
            Aporte: ${wheel.aporte}
        """.trimIndent())
    }
    context.startActivity(Intent.createChooser(compartir, "Compartir viaje"))
}
