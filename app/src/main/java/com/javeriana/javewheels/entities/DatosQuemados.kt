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
val solicitudConductorDePrueba = PasajeroViaje(
    "david-santiago", "David Santiago Gomez", "Parkway · Calle 39 con Carrera 21"
)

val proximoViajeConductor = Viaje(
    origen = "Salitre",
    destino = "Javeriana",
    fecha = "Mañana",
    hora = "7:00 a. m.",
    cuposOcupados = 1,
    cuposTotales = 3,
    estado = "Programado",
    pasajerosConfirmados = listOf(PasajeroViaje("fulana-perez", "Fulana Perez", "Javeriana")),
    solicitudes = listOf(solicitudConductorDePrueba)
)

/** Destino del acceso rápido "Ir a la Javeriana". */
const val DESTINO_JAVERIANA = "Javeriana"

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
        distancia = "120 m", distanciaMetros = 120, aportePesos = 3000, salidaMinutos = 455
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
    ),
    WheelPasajero(
        id = "camila-salitre", conductor = "Camila Torres", origen = "Salitre", destino = "Javeriana",
        fecha = "Mañana, 1 oct.", hora = "8:15 a. m.", aporte = "$5.000", cuposDisponibles = 3,
        modoBuseta = false, compatibilidad = "A 700 m · Recogida directa", distancia = "700 m",
        horaRecogida = "8:20 a. m.", distanciaMetros = 700, aportePesos = 5000, salidaMinutos = 495
    ),
    WheelPasajero(
        id = "daniel-chapinero", conductor = "Daniel Ruiz", origen = "Chapinero", destino = "Javeriana",
        fecha = "Mañana, 1 oct.", hora = "9:00 a. m.", aporte = "$2.000", cuposDisponibles = 1,
        modoBuseta = true, compatibilidad = "A 200 m · A pie: 3 min", distancia = "200 m",
        horaRecogida = "9:05 a. m.", tiempoAPie = "3 min", distanciaMetros = 200, aportePesos = 2000, salidaMinutos = 540
    ),
    WheelPasajero(
        id = "sofia-regreso", conductor = "Sofía López", origen = "Javeriana", destino = "Chapinero",
        fecha = "Mañana, 1 oct.", hora = "5:30 p. m.", aporte = "$3.000", cuposDisponibles = 2,
        modoBuseta = false, compatibilidad = "A 150 m · Recogida directa", distancia = "150 m",
        puntoRecogida = "Entrada principal de la Javeriana", distanciaMetros = 150,
        aportePesos = 3000, salidaMinutos = 1050, horaRecogida = "5:30 p. m."
    ),
    WheelPasajero(
        id = "miguel-regreso", conductor = "Miguel Castro", origen = "Javeriana", destino = "Salitre",
        fecha = "Mañana, 1 oct.", hora = "6:00 p. m.", aporte = "$6.000", cuposDisponibles = 3,
        modoBuseta = true, compatibilidad = "A 500 m · A pie: 6 min", distancia = "500 m",
        tiempoAPie = "6 min", puntoRecogida = "Entrada principal de la Javeriana",
        horaRecogida = "6:05 p. m.", distanciaMetros = 500, aportePesos = 6000, salidaMinutos = 1080
    )
).map { wheel ->
    wheel.copy(fecha = fechaBusqueda(wheel.salidaMillis), hora = horaBusqueda(wheel.salidaMillis))
}.let { programados ->
    val referencia = System.currentTimeMillis()
    programados + programados.mapIndexed { indice, wheel ->
        val salida = referencia + (15 + indice * 7) * 60_000L
        wheel.copy(id = "${wheel.id}-ahora", salidaMillis = salida,
            salidaMinutos = minutosDelDia(salida), fecha = fechaBusqueda(salida),
            hora = horaBusqueda(salida), horaRecogida = horaBusqueda(salida + 5 * 60_000L))
    } + programados.map { wheel ->
        val salida = salidaEnDias(2, wheel.salidaMinutos, referencia)
        wheel.copy(id = "${wheel.id}-pasado-manana", salidaMillis = salida, fecha = fechaBusqueda(salida))
    }
}

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
            hora = "8:00 a. m.", salidaMinutos = 480
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
            hora = "5:30 p. m.", salidaMinutos = 1050, horaRecogida = "5:30 p. m.", puntoRecogida = "Entrada principal de la Javeriana"
        ),
        estado = "Finalizado",
        detalleRecogida = "Recogida directa · Chapinero"
    )
)

/** Recorridos publicados por el usuario en modo conductor. */
val viajesProgramadosConductor = listOf(
    proximoViajeConductor,
    proximoViajeConductor.copy(
        id = "conductor-programado-chapinero",
        origen = "Javeriana", destino = "Chapinero", fecha = "Vie., 2 oct.",
        hora = "5:30 p. m.", cuposOcupados = 1, aporte = "$3.000", puntoRecogida = "Entrada principal de la Javeriana"
    )
)

val viajesFinalizadosConductor = listOf(
    proximoViajeConductor.copy(id = "conductor-finalizado-salitre", fecha = "Mar., 29 sept.", estado = "Finalizado", cuposOcupados = 3,
        pasajerosConfirmados = emptyList(), solicitudes = emptyList()),
    proximoViajeConductor.copy(
        id = "conductor-finalizado-regreso",
        origen = "Javeriana", destino = "Salitre", fecha = "Lun., 28 sept.",
        hora = "5:30 p. m.", estado = "Finalizado", cuposOcupados = 2, puntoRecogida = "Entrada principal de la Javeriana",
        pasajerosConfirmados = emptyList(), solicitudes = emptyList()
    )
)

val rutasGuardadas = listOf(
    RutaGuardada("casa-universidad", "Casa → Universidad", "Salitre", "Javeriana"),
    RutaGuardada("chapinero-universidad", "Chapinero → Universidad", "Chapinero", "Javeriana"),
    RutaGuardada("universidad-chapinero", "Universidad → Chapinero", "Javeriana", "Chapinero"),
    RutaGuardada("universidad-casa", "Universidad → Casa", "Javeriana", "Salitre")
)
