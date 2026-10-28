package com.javeriana.javewheels.ui.components

import android.content.Context
import android.content.Intent
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.WheelPasajero

/** Menú nativo compartido por el detalle de reserva y el viaje en vivo. */
fun compartirViaje(context: Context, wheel: WheelPasajero) {
    val compartir = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.compartir_asunto))
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.compartir_contenido,
            wheel.origen, wheel.destino, wheel.fecha, wheel.hora, wheel.conductor,
            wheel.vehiculo, wheel.puntoRecogida, wheel.horaRecogida, wheel.aporte))
    }
    context.startActivity(Intent.createChooser(compartir, context.getString(R.string.ui_compartir_viaje)))
}
