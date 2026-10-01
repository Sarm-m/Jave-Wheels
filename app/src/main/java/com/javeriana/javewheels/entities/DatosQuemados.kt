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

/** Usuario de prueba que se muestra en Perfil. */
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

/** Wheels compatibles con la búsqueda del pasajero. */
val wheelsDisponibles = listOf(
    WheelPasajero(
        id = "andres-directo",
        conductor = "Andrés Rojas",
        origen = "Salitre",
        destino = "Javeriana",
        fecha = "Mañana, 1 oct.",
        hora = "7:35 a. m.",
        aporte = "$3.000",
        cuposDisponibles = 2,
        modoBuseta = false,
        compatibilidad = "A 120 m · Recogida directa",
        distancia = "120 m"
    ),
    WheelPasajero(
        id = "laura-buseta",
        conductor = "Laura Gómez",
        origen = "Salitre",
        destino = "Javeriana",
        fecha = "Mañana, 1 oct.",
        hora = "7:30 a. m.",
        aporte = "$4.000",
        cuposDisponibles = 2,
        modoBuseta = true,
        compatibilidad = "A 350 m · A pie: 4 min"
    )
)

/** Reservas activas y solicitudes pendientes del pasajero. */
val reservasPasajero = listOf(
    ReservaPasajero(
        wheel = wheelsDisponibles[1].copy(
            id = "alexandra-confirmada",
            conductor = "Alexandra Ramos"
        ),
        estado = "Confirmada",
        detalleRecogida = "Modo Buseta · 350 m al punto"
    ),
    ReservaPasajero(
        wheel = wheelsDisponibles[0].copy(
            id = "andres-pendiente",
            origen = "Chapinero",
            fecha = "Vie., 2 oct.",
            hora = "8:00 a. m."
        ),
        estado = "Pendiente",
        detalleRecogida = "Esperando respuesta del conductor"
    )
)

val reservaConfirmada = reservasPasajero.first()

/** Wheels finalizados que aparecen en el historial del pasajero. */
val wheelsFinalizados = listOf(
    ReservaPasajero(
        wheel = reservaConfirmada.wheel.copy(
            id = "alexandra-finalizado",
            fecha = "Mar., 29 sept."
        ),
        estado = "Finalizado",
        detalleRecogida = "Modo Buseta · Calle 72"
    ),
    ReservaPasajero(
        wheel = wheelsDisponibles[0].copy(
            id = "andres-finalizado",
            origen = "Javeriana",
            destino = "Chapinero",
            fecha = "Lun., 28 sept.",
            hora = "5:30 p. m."
        ),
        estado = "Finalizado",
        detalleRecogida = "Recogida directa · Chapinero"
    )
)
