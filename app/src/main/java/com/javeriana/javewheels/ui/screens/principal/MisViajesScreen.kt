package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.javeriana.javewheels.ui.components.PantallaEnConstruccion
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// Pestaña "Mis viajes".
// ============================================================

@Composable
fun MisViajesScreen(modifier: Modifier = Modifier) {
    PantallaEnConstruccion(
        titulo = "Mis viajes",
        icono = Icons.Default.DateRange,
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun MisViajesScreenPreview() {
    JaveWheelsTheme {
        MisViajesScreen()
    }
}
