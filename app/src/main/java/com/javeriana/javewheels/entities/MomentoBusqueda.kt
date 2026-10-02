package com.javeriana.javewheels.entities

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val VENTANA_BUSQUEDA_MILLIS = 60 * 60 * 1000L

fun salidaEnDias(dias: Int, minutos: Int, referencia: Long = System.currentTimeMillis()): Long =
    Calendar.getInstance().apply {
        timeInMillis = referencia
        add(Calendar.DAY_OF_YEAR, dias)
        set(Calendar.HOUR_OF_DAY, minutos / 60)
        set(Calendar.MINUTE, minutos % 60)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

fun fechaBusqueda(momento: Long): String =
    SimpleDateFormat("EEE, d MMM yyyy", Locale.forLanguageTag("es-CO")).format(Date(momento))

fun horaBusqueda(momento: Long): String =
    SimpleDateFormat("h:mm a", Locale.forLanguageTag("es-CO")).format(Date(momento))

fun minutosDelDia(momento: Long): Int = Calendar.getInstance().apply { timeInMillis = momento }.let {
    it.get(Calendar.HOUR_OF_DAY) * 60 + it.get(Calendar.MINUTE)
}

/** Ventana inclusiva de una hora: excluye salidas anteriores y respeta la fecha. */
fun coincideMomento(salida: Long, desde: Long): Boolean =
    salida >= desde && salida - desde <= VENTANA_BUSQUEDA_MILLIS
