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
    val estado: String
)

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

data class Chat(
    val id: Int,
    val nombreUsuario: String,
    val mensajes: List<Mensaje>
) {
    val ultimoMensaje: Mensaje? get() = mensajes.lastOrNull()
}