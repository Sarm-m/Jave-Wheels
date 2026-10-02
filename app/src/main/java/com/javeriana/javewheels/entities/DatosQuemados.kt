package com.javeriana.javewheels.entities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.ShareLocation

// ============================================================
// DATOS QUEMADOS (hardcoded)
// ============================================================

/** Dominio que deben tener los correos para poder entrar. */
const val DOMINIO_INSTITUCIONAL = "@javeriana.edu.co"

/** Código de verificación "enviado" al correo (quemado para pruebas). */
const val CODIGO_VERIFICACION = "123456"

/** Mínimo de caracteres de una contraseña. */
const val MINIMO_CONTRASENA = 8

/** Usuario de prueba que se muestra en Perfil (sin vehículo: todavía no es conductor). */
val usuarioDePrueba = Usuario(
    nombre = "Javeriano de prueba",
    correo = "javeriano$DOMINIO_INSTITUCIONAL",
    celular = "300 123 4567"
)

/** Las 3 páginas de la pantalla de carga. */
val paginasCarga = listOf(
    PaginaCarga(
        titulo = "Reserva tu Wheel",
        descripcion = "Encuentra conductores de la comunidad Javeriana que van por tu ruta y reserva tu cupo en segundos.",
        icono = Icons.Default.DirectionsCar
    ),
    PaginaCarga(
        titulo = "Viaja seguro",
        descripcion = "Sigue tu viaje en tiempo real y comparte tu recorrido con quien quieras.",
        icono = Icons.Default.ShareLocation
    ),
    PaginaCarga(
        titulo = "Pasajero o conductor",
        descripcion = "Cambia de rol cuando quieras: pide un Wheel o comparte tu camino con otros javerianos.",
        icono = Icons.Default.PeopleAlt
    )
)

/** Próximo viaje del conductor (tarjeta de "Conductor - Inicio"). */
val proximoViajeConductor = Viaje(
    origen = "Salitre",
    destino = "Javeriana",
    fecha = "Mañana",
    hora = "7:00 a. m.",
    cuposOcupados = 2,
    cuposTotales = 3,
    estado = "Programado"
)

/** Destino del acceso rápido "Ir a la Javeriana". */
const val DESTINO_JAVERIANA = "Pontificia Universidad Javeriana"

/** Año más antiguo que se acepta para un vehículo. */
const val ANIO_MINIMO_VEHICULO = 1980

const val TIEMPO_RESPUESTA_CHAT = 1500L

val chatsQuemados = listOf(
    Chat(
        id = 1,
        nombreUsuario = "Camila Rojas",
        mensajes = listOf(
            Mensaje("¿Vas hoy para la Javeriana?", esMio = false),
            Mensaje("Hola", esMio = true)
        )
    ),
    Chat(
        id = 2,
        nombreUsuario = "Andrés Gómez",
        mensajes = listOf(
            Mensaje("¿A qué hora sales?", esMio = true),
            Mensaje("Llego en 30 minutos", esMio = false)
        )
    ),
    Chat(
        id = 3,
        nombreUsuario = "Laura Martínez",
        mensajes = listOf(
            Mensaje("Gracias por el viaje de ayer", esMio = false),
            Mensaje("Con gusto, nos vemos mañana", esMio = true),
            Mensaje("Perfecto, ahí estaré", esMio = false)
        )
    ),
    Chat(
        id = 4,
        nombreUsuario = "Santiago Pérez",
        mensajes = listOf(
            Mensaje("¿Tienes cupo para el viernes?", esMio = true),
            Mensaje("Sí, queda 1 cupo", esMio = false)
        )
    ),
    Chat(
        id = 5,
        nombreUsuario = "Valentina Cruz",
        mensajes = listOf(
            Mensaje("Ya estoy en la entrada", esMio = false)
        )
    )
)

val respuestasQuemadas = listOf(
    "Listo, perfecto",
    "Voy en camino",
    "Dale, nos vemos allá",
    "Gracias por avisar",
    "Salgo en 2",
    "Ok"
)