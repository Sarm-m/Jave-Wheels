package com.javeriana.javewheels.entities

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FiltrosWheelsTest {
    private fun wheel(id: String, origen: String = "Salitre", destino: String = "Javeriana", distancia: Int = 200,
                      aporte: Int = 3000, salida: Int = 480, cupos: Int = 2, buseta: Boolean = false) =
        WheelPasajero(id, "Conductor", origen, destino, "Fecha", "Hora", "Aporte", cupos, buseta,
            "Recogida", distanciaMetros = distancia, aportePesos = aporte, salidaMinutos = salida)

    @Test fun ubicacionSinRestriccionYAliasInstitucional() {
        val viajes = listOf(wheel("a"), wheel("b", origen = "Chapinero"), wheel("c", destino = "Salitre"))
        assertEquals(listOf("a", "b"), filtrarWheels(viajes, " Mi ubicación ", "Pontificia Universidad Javeriana").map { it.id })
    }

    @Test fun rutaGuardadaRespetaAmbosExtremos() {
        val viajes = listOf(wheel("a"), wheel("b", origen = "Chapinero"), wheel("c", destino = "Salitre"))
        assertEquals(listOf("a"), filtrarWheels(viajes, " SALITRE ", "javeriana").map { it.id })
        assertTrue(filtrarWheels(viajes, "Zona inexistente", "Javeriana").isEmpty())
    }

    @Test fun limitesDeDistanciaAporteYCuposIncluyenIgualdad() {
        val viajes = listOf(wheel("limite", distancia = 350, aporte = 4000, cupos = 2),
            wheel("lejano", distancia = 351), wheel("costoso", aporte = 4001), wheel("sinCupos", cupos = 1))
        assertEquals(listOf("limite"), filtrarWheels(viajes, "", "", distanciaMaxima = 350,
            aporteMaximo = 4000, cuposMinimos = 2).map { it.id })
    }

    @Test fun horariosNoSeSuperponenEnLasOchoYMediodia() {
        val viajes = listOf(wheel("temprano", salida = 479), wheel("ocho", salida = 480),
            wheel("manana", salida = 719), wheel("mediodia", salida = 720))
        assertEquals(listOf("temprano"), filtrarWheels(viajes, "", "", horario = 1).map { it.id })
        assertEquals(listOf("ocho", "manana"), filtrarWheels(viajes, "", "", horario = 2).map { it.id })
        assertEquals(listOf("mediodia"), filtrarWheels(viajes, "", "", horario = 3).map { it.id })
    }

    @Test fun busetaSeCombinaConOtrosFiltros() {
        val viajes = listOf(wheel("buseta", buseta = true), wheel("directo"), wheel("lejana", buseta = true, distancia = 800))
        assertEquals(listOf("buseta"), filtrarWheels(viajes, "", "", distanciaMaxima = 350, soloBuseta = true).map { it.id })
    }

    @Test fun restablecerRecuperaResultadosYNoModificaLaLista() {
        val viajes = listOf(wheel("a"), wheel("b", aporte = 6000))
        assertTrue(filtrarWheels(viajes, "", "", aporteMaximo = 2000).isEmpty())
        assertEquals(viajes, filtrarWheels(viajes, "", ""))
        assertEquals(2, viajes.size)
    }
}
