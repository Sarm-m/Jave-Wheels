package com.javeriana.javewheels.viewmodels

import com.javeriana.javewheels.R
import org.junit.Assert.*
import org.junit.Test

class ErroresFormularioTest {
    @Test fun acceso_resuelve_el_error_y_lo_limpia_al_corregir() {
        val modelo = LoginViewModel()
        assertFalse(modelo.iniciarSesion())
        assertEquals(R.string.error_completa_tu_correo_y_tu_contrasena, modelo.uiState.value.mensajeError)
        modelo.actualizarCorreo("prueba@javeriana.edu.co")
        modelo.actualizarContrasena("prueba123")
        assertTrue(modelo.iniciarSesion())
        assertEquals(0, modelo.uiState.value.mensajeError)
    }

    @Test fun vehiculo_con_placa_incompleta_no_confirma_el_formulario() {
        val modelo = VehiculoViewModel().apply {
            actualizarPlaca("AB123"); actualizarMarca("Renault"); actualizarModelo("Sandero")
            actualizarAnio("2022"); actualizarColor("Gris"); actualizarCupos("3")
        }
        assertFalse(modelo.guardar())
        assertEquals(R.string.error_la_placa_debe_tener_3_letras_y_3_numeros_ej_abc123,
            modelo.uiState.value.mensajeError)
        modelo.actualizarPlaca("abc123")
        assertTrue(modelo.guardar())
        assertEquals("ABC123", modelo.uiState.value.placa)
        assertEquals(0, modelo.uiState.value.mensajeError)
    }

    @Test fun edicion_de_perfil_conserva_la_contrasena_opcional() {
        val modelo = PerfilViewModel()
        modelo.iniciarEdicion()
        assertTrue(modelo.guardar())
        modelo.actualizarNombre("")
        assertFalse(modelo.guardar())
        assertEquals(R.string.error_escribe_tu_nombre_completo, modelo.uiState.value.mensajeError)
    }
}
