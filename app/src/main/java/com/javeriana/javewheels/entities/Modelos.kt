package com.javeriana.javewheels.entities

import androidx.compose.ui.graphics.vector.ImageVector

// ============================================================
// Modelos de datos
// ============================================================

enum class Rol(val titulo: String) {
    PASAJERO("Pasajero"),
    CONDUCTOR("Conductor")
}

data class PaginaCarga(
    val titulo: String,
    val descripcion: String,
    val icono: ImageVector
)

data class Viaje(
    val origen: String,
    val destino: String,
    val fecha: String,
    val hora: String,
    val cuposOcupados: Int,
    val cuposTotales: Int,
    val estado: String,
    val aporte: String = "$4.000",
    val id: String = "conductor-programado-salitre",
    val vehiculo: String = "Renault Sandero · 2022 · Gris · ABC123",
    val puntoRecogida: String = "Calle 72 con Cra. 11",
    val modoBuseta: Boolean = true,
    val pasajerosConfirmados: List<PasajeroViaje> = emptyList(),
    val solicitudes: List<PasajeroViaje> = emptyList()
) {
    val totalRecaudado: Int
        get() = (aporte.filter { it.isDigit() }.toIntOrNull() ?: 0) * cuposOcupados
}

data class PasajeroViaje(val id: String, val nombre: String, val puntoRecogida: String)

/** Datos básicos del usuario que inició sesión. */
data class Usuario(
    val nombre: String,
    val correo: String,
    val celular: String,
    val placa: String? = null,
    val marca: String? = null,
    val modelo: String? = null,
    val anio: Int? = null,
    val color: String? = null,
    val cupos: Int? = null
)

data class Mensaje(
    val texto: String,
    val esMio: Boolean
)

/** Datos de un Wheel que puede consultar un pasajero. */
data class WheelPasajero(
    val id: String,
    val conductor: String,
    val origen: String,
    val destino: String,
    val fecha: String,
    val hora: String,
    val aporte: String,
    val cuposDisponibles: Int,
    val modoBuseta: Boolean,
    val compatibilidad: String,
    val vehiculo: String = "Renault Sandero · 2022 · Gris · ABC123",
    val calificacion: String = "4,8 / 5",
    val puntoRecogida: String = "Calle 72 con Cra. 11",
    val distancia: String = "350 m",
    val tiempoAPie: String = "4 min",
    val horaRecogida: String = "7:42 a. m.",
    val distanciaMetros: Int = 350,
    val aportePesos: Int = 4000,
    val salidaMinutos: Int = 450,
    val salidaMillis: Long = salidaEnDias(1, salidaMinutos)
)

/** Reserva o solicitud del pasajero para un Wheel. */
data class ReservaPasajero(
    val wheel: WheelPasajero,
    val estado: String,
    val detalleRecogida: String
)

data class Chat(
    val id: Int,
    val nombreUsuario: String,
    val mensajes: List<Mensaje>
) {
    val ultimoMensaje: Mensaje? get() = mensajes.lastOrNull()
}
