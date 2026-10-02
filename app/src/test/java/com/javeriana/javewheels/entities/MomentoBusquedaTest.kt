package com.javeriana.javewheels.entities

import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar

class MomentoBusquedaTest {
    private val referencia = Calendar.getInstance().apply {
        set(2026, Calendar.OCTOBER, 1, 23, 45, 0); set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    @Test fun ventanaIncluyeLimitesYDescartaPasados() {
        assertFalse(coincideMomento(referencia - 1, referencia))
        assertTrue(coincideMomento(referencia, referencia))
        assertTrue(coincideMomento(referencia + VENTANA_BUSQUEDA_MILLIS, referencia))
        assertFalse(coincideMomento(referencia + VENTANA_BUSQUEDA_MILLIS + 1, referencia))
    }

    @Test fun ventanaCruzaMedianocheSinConfundirOtroDia() {
        assertTrue(coincideMomento(salidaEnDias(1, 15, referencia), referencia))
        assertFalse(coincideMomento(salidaEnDias(2, 15, referencia), referencia))
    }

    @Test fun programarCombinaFechaConFiltrosExistentes() {
        val manana = salidaEnDias(1, 450, referencia)
        val base = WheelPasajero("a", "Conductor", "Salitre", "Javeriana", "Fecha", "Hora", "$3.000",
            2, true, "Cerca", salidaMillis = manana)
        val viajes = listOf(base, base.copy(id = "otro-dia", salidaMillis = salidaEnDias(2, 450, referencia)),
            base.copy(id = "caro", aportePesos = 6000))
        assertEquals(listOf("a"), filtrarWheels(viajes, "Salitre", "Javeriana",
            aporteMaximo = 4000, soloBuseta = true, desdeMillis = manana).map { it.id })
    }
}
