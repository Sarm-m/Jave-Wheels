package com.javeriana.javewheels.entities

import androidx.compose.ui.graphics.vector.ImageVector

// ============================================================
// Modelos de datos
// ============================================================

/** Rol con el que el usuario está usando la app. */
enum class Rol(val titulo: String) {
    PASAJERO("Pasajero"),
    CONDUCTOR("Conductor")
}

/** Una página de la pantalla de carga (Pantalla Carga 1, 2 y 3 de Figma). */
data class PaginaCarga(
    val titulo: String,
    val descripcion: String,
    val icono: ImageVector
)

/** Un viaje (Wheel) publicado por un conductor. */
data class Viaje(
    val origen: String,
    val destino: String,
    val fecha: String,
    val hora: String,
    val cuposOcupados: Int,
    val cuposTotales: Int,
    val estado: String
)

/** Datos básicos del usuario que inició sesión. */
data class Usuario(
    val nombre: String,
    val correo: String,
    val celular: String
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
    val horaRecogida: String = "7:42 a. m."
)

/** Reserva o solicitud del pasajero para un Wheel. */
data class ReservaPasajero(
    val wheel: WheelPasajero,
    val estado: String,
    val detalleRecogida: String
)
