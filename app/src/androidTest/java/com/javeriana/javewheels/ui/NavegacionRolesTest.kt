package com.javeriana.javewheels.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.javeriana.javewheels.MainActivity
import org.junit.Rule
import org.junit.Test

class NavegacionRolesTest {
    @get:Rule val regla = createAndroidComposeRule<MainActivity>()

    private fun entrar() {
        regla.onNodeWithText("Toca la pantalla para continuar").performClick()
        regla.onNodeWithText("Saltar").performClick()
        regla.onNode(hasSetTextAction() and hasText("Correo institucional"))
            .performTextInput("prueba@javeriana.edu.co")
        regla.onNode(hasSetTextAction() and hasText("Contraseña"))
            .performTextInput("prueba123")
        regla.onNodeWithText("Iniciar sesión").performScrollTo().performClick()
    }

    @Test fun registro_vehiculo_cambio_rol_historial_y_reservas() {
        entrar()
        regla.onNodeWithText("Pasajero").performClick()
        regla.onNodeWithText("Registrar mi vehículo").performScrollTo().performClick()
        listOf("Placa" to "XYZ987", "Marca" to "Renault", "Modelo" to "Sandero",
            "Año" to "2022", "Color" to "Gris", "Cupos disponibles" to "3").forEach { (campo, valor) ->
            regla.onNode(hasSetTextAction() and hasText(campo + "\u00A0*")).performScrollTo().performTextInput(valor)
        }
        regla.onNodeWithText("Guardar vehículo").performScrollTo().performClick()
        regla.onNodeWithText("Ir al inicio del conductor").performScrollTo().performClick()
        regla.onNodeWithText("Conductor").assertIsDisplayed()
        regla.onNodeWithText("Mis viajes").performClick()
        regla.onNodeWithText("Programados").assertIsDisplayed()
        regla.onNodeWithText("Finalizados").performClick()
        regla.onAllNodesWithText("Ver resumen").onFirst().performScrollTo().performClick()
        regla.onNodeWithText("Resumen del viaje").assertIsDisplayed()
        regla.onNodeWithText("ABC 123").performScrollTo().assertIsDisplayed()
        regla.onNodeWithContentDescription("Volver").performClick()
        regla.onNodeWithText("Conductor").performScrollTo().performClick()
        regla.onNodeWithText("Reservas").assertIsDisplayed()
        regla.onNodeWithText("Ver reserva").performScrollTo().performClick()
        regla.onNodeWithText("Detalle de reserva").assertIsDisplayed()
        regla.onNodeWithText("ABC 123").performScrollTo().assertIsDisplayed()
    }

    @Test fun perfil_edicion_y_dialogo_fotografia() {
        entrar()
        regla.onNodeWithText("Perfil").performClick()
        regla.onNodeWithText("Editar").performScrollTo().performClick()
        regla.onNodeWithText("Editar foto").performScrollTo().performClick()
        regla.onNodeWithText("Elegir de la galería").assertIsDisplayed()
        regla.onNodeWithText("Cancelar").performClick()
        regla.onNodeWithText("Guardar").performScrollTo().performClick()
        regla.onNodeWithText("¡Tu perfil fue actualizado correctamente!").assertIsDisplayed()
        regla.onNodeWithText("Ir al menú principal").performScrollTo().performClick()
        regla.onNodeWithText("Pasajero").assertIsDisplayed()
    }
}
