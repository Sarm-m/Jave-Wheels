package com.javeriana.javewheels.entities

import java.text.Normalizer
import java.util.Locale

private fun zonaNormalizada(valor: String): String {
    val limpia = Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
        .replace(Regex("\\p{M}"), "").lowercase(Locale.ROOT)
    return if (limpia == "pontificia universidad javeriana") "javeriana" else limpia
}

fun filtrarWheels(
    wheels: List<WheelPasajero>, origen: String, destino: String,
    distanciaMaxima: Int = 1000, horario: Int = 0, cuposMinimos: Int = 1,
    aporteMaximo: Int = 6000, soloBuseta: Boolean = false, desdeMillis: Long? = null
): List<WheelPasajero> {
    val desde = zonaNormalizada(origen)
    val hasta = zonaNormalizada(destino)
    return wheels.filter { wheel ->
        (desdeMillis == null || coincideMomento(wheel.salidaMillis, desdeMillis)) &&
        (desde.isBlank() || desde == "mi ubicacion" || zonaNormalizada(wheel.origen) == desde) &&
        (hasta.isBlank() || zonaNormalizada(wheel.destino) == hasta) &&
        wheel.distanciaMetros <= distanciaMaxima && wheel.cuposDisponibles >= cuposMinimos &&
        wheel.aportePesos <= aporteMaximo && (!soloBuseta || wheel.modoBuseta) &&
        when (horario) {
            1 -> wheel.salidaMinutos < 480
            2 -> wheel.salidaMinutos in 480 until 720
            3 -> wheel.salidaMinutos >= 720
            else -> true
        }
    }
}
