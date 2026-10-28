package com.javeriana.javewheels.ui.screens.pasajero

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.javeriana.javewheels.entities.reservaConfirmada
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.components.SeguimientoViajePasajero
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun VerViajeBusetaScreen(
    onVolver: () -> Unit = {},
    onCentrar: () -> Unit = {},
    onCompartirViaje: () -> Unit = {},
    mostrarBarraNavegacion: Boolean = true,
    onSeleccionarPestana: (Pestana) -> Unit = {}
) {
    SeguimientoViajePasajero(
        wheel = reservaConfirmada.wheel,
        modoBuseta = true,
        onVolver = onVolver,
        onCentrar = onCentrar,
        onCompartir = onCompartirViaje,
        mostrarBarraNavegacion = mostrarBarraNavegacion,
        onSeleccionarPestana = onSeleccionarPestana
    )
}

@Preview(showBackground = true, widthDp = 320, fontScale = 1.5f)
@Composable
private fun VerViajeBusetaScreenPreview() {
    JaveWheelsTheme { VerViajeBusetaScreen() }
}
