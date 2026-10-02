package com.javeriana.javewheels.viewmodels

import com.javeriana.javewheels.entities.*
import org.junit.Assert.*
import org.junit.Test

class ViajesConductorTest {
    @Test fun publicarConservaLosDatosYLosOtrosViajes() {
        val viewModel = InicioViewModel()
        val anteriores = viewModel.uiState.value.viajesPublicados
        val editado = proximoViajeConductor.copy(origen = "Calle 45", destino = "Chapinero",
            fecha = "3 oct.", hora = "9:30 a. m.", aporte = "$5.000", vehiculo = "Kia · ABC123",
            cuposTotales = 2, modoBuseta = false, puntoRecogida = "Calle 45")
        val id = viewModel.publicarViaje(editado)
        val publicado = viewModel.uiState.value.viajesPublicados.first()

        assertEquals(id, publicado.id)
        assertEquals(editado.origen, publicado.origen)
        assertEquals(editado.destino, publicado.destino)
        assertEquals(editado.fecha, publicado.fecha)
        assertEquals(editado.hora, publicado.hora)
        assertEquals(editado.aporte, publicado.aporte)
        assertEquals(editado.vehiculo, publicado.vehiculo)
        assertEquals(2, publicado.cuposTotales)
        assertFalse(publicado.modoBuseta)
        assertEquals(0, publicado.cuposOcupados)
        assertTrue(publicado.pasajerosConfirmados.isEmpty())
        assertEquals(anteriores, viewModel.uiState.value.viajesPublicados.drop(1))
        assertEquals(publicado, viewModel.uiState.value.proximoViaje)
        assertNotEquals(id, viewModel.publicarViaje(editado))
    }

    @Test fun aceptarAgregaDavidConFulanaYActualizaCuposSoloEnSuViaje() {
        val viewModel = InicioViewModel()
        val otro = viewModel.uiState.value.viajesPublicados.last()
        assertTrue(viewModel.aceptarSolicitud(proximoViajeConductor.id, solicitudConductorDePrueba.id))
        val viaje = requireNotNull(viewModel.uiState.value.proximoViaje)

        assertEquals(2, viaje.cuposOcupados)
        assertEquals(listOf("Fulana Perez", "David Santiago Gomez"), viaje.pasajerosConfirmados.map { it.nombre })
        assertTrue(viaje.solicitudes.isEmpty())
        assertEquals(otro, viewModel.uiState.value.viajesPublicados.last())
        assertFalse(viewModel.aceptarSolicitud(viaje.id, solicitudConductorDePrueba.id))
        assertEquals(2, viewModel.uiState.value.proximoViaje?.cuposOcupados)
    }

    @Test fun noAceptaSolicitudesCuandoElViajeEstaLleno() {
        val lleno = proximoViajeConductor.copy(cuposOcupados = 3)
        val viewModel = InicioViewModel(InicioUiState(proximoViaje = lleno, viajesPublicados = listOf(lleno)))

        assertFalse(viewModel.aceptarSolicitud(lleno.id, solicitudConductorDePrueba.id))
        assertEquals(lleno, viewModel.uiState.value.viajesPublicados.single())
    }

    @Test fun rechazarNoModificaPasajerosConfirmadosNiCupos() {
        val viewModel = InicioViewModel()
        viewModel.rechazarSolicitud(proximoViajeConductor.id, solicitudConductorDePrueba.id)
        val viaje = requireNotNull(viewModel.uiState.value.proximoViaje)
        assertTrue(viaje.solicitudes.isEmpty())
        assertEquals(proximoViajeConductor.pasajerosConfirmados, viaje.pasajerosConfirmados)
        assertEquals(1, viaje.cuposOcupados)
    }

    @Test fun recaudoUsaLosPasajerosQueViajaronYElAporte() {
        val viaje = proximoViajeConductor.copy(cuposOcupados = 3, aporte = "$4.000")
        assertEquals(12000, viaje.totalRecaudado)
        assertEquals("$12.000", formatoPesos(viaje.totalRecaudado))
        assertEquals(6000, viaje.copy(cuposOcupados = 2, aporte = "$3.000").totalRecaudado)
        assertEquals(0, viaje.copy(cuposOcupados = 0).totalRecaudado)
    }

    @Test fun cancelarElProximoViajeConservaElOtroYActualizaInicio() {
        val viewModel = InicioViewModel()
        val otro = viewModel.uiState.value.viajesPublicados.last()
        assertTrue(viewModel.cancelarViaje(proximoViajeConductor.id))
        assertEquals(listOf(otro), viewModel.uiState.value.viajesPublicados)
        assertEquals(otro, viewModel.uiState.value.proximoViaje)
        assertFalse(viewModel.cancelarViaje(proximoViajeConductor.id))
        assertFalse(viewModel.aceptarSolicitud(proximoViajeConductor.id, solicitudConductorDePrueba.id))
    }

    @Test fun cancelarOtroViajeMantieneElProximo() {
        val viewModel = InicioViewModel()
        assertTrue(viewModel.cancelarViaje(viewModel.uiState.value.viajesPublicados.last().id))
        assertEquals(proximoViajeConductor, viewModel.uiState.value.proximoViaje)
        assertEquals(listOf(proximoViajeConductor), viewModel.uiState.value.viajesPublicados)
    }

    @Test fun cancelarTodosDejaInicioSinViajes() {
        val viewModel = InicioViewModel()
        viewModel.uiState.value.viajesPublicados.forEach { assertTrue(viewModel.cancelarViaje(it.id)) }
        assertTrue(viewModel.uiState.value.viajesPublicados.isEmpty())
        assertNull(viewModel.uiState.value.proximoViaje)
        val estado = viewModel.uiState.value
        assertFalse(viewModel.cancelarViaje("no-existe"))
        assertEquals(estado, viewModel.uiState.value)
        val publicado = viewModel.publicarViaje(proximoViajeConductor)
        assertEquals(publicado, viewModel.uiState.value.proximoViaje?.id)
    }

    @Test fun resumenDeVehiculoConservaLosDatosRegistradosYEditados() {
        val viewModel = VehiculoViewModel()
        viewModel.actualizarPlaca("abc123")
        viewModel.actualizarMarca("Chevrolet")
        viewModel.actualizarModelo("Spark")
        viewModel.actualizarAnio("2020")
        viewModel.actualizarColor("Gris")
        viewModel.actualizarCupos("4")
        assertTrue(viewModel.guardar())
        assertEquals("Chevrolet · Spark · 2020 · Gris · ABC123", viewModel.uiState.value.resumen)
        viewModel.actualizarModelo("Onix")
        viewModel.actualizarColor("Negro")
        assertEquals("Chevrolet · Onix · 2020 · Negro · ABC123", viewModel.uiState.value.resumen)
    }

    @Test fun cerrarSesionLimpiaViajesPublicadosYSolicitudesAceptadas() {
        val viewModel = InicioViewModel()
        viewModel.publicarViaje(proximoViajeConductor)
        viewModel.aceptarSolicitud(proximoViajeConductor.id, solicitudConductorDePrueba.id)
        viewModel.cerrarSesion()
        assertEquals(InicioUiState(), viewModel.uiState.value)
    }
}
