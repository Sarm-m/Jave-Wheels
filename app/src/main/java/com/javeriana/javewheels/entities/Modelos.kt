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
