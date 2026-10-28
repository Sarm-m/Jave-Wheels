package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.PantallaEnConstruccion
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// Pestaña "Mensajes".
// ============================================================

@Composable
fun MensajesScreen(modifier: Modifier = Modifier) {
    PantallaEnConstruccion(
        titulo = stringResource(R.string.ui_mensajes),
        icono = Icons.Outlined.ChatBubbleOutline,
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun MensajesScreenPreview() {
    JaveWheelsTheme {
        MensajesScreen()
    }
}
