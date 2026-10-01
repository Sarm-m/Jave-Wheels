package com.javeriana.javewheels.ui.screens.acceso

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.IconoConfirmacion
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// "Acceso - Confirmación".
// ============================================================

@Composable
fun ConfirmacionScreen(
    onIrALogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MarcaJaveWheels(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.weight(1f))
        IconoConfirmacion()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "¡Todo listo!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextoAyuda(
            texto = "Ya puedes iniciar sesión con tu\ncorreo y contraseña.",
            centrado = true
        )
        Spacer(modifier = Modifier.height(40.dp))
        BotonPrincipal(texto = "Ir a iniciar sesión", onClick = onIrALogin)
        Spacer(modifier = Modifier.weight(1f))

        TextoAyuda(texto = "Tu próximo Wheel te espera.", centrado = true)
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConfirmacionScreenPreview() {
    JaveWheelsTheme {
        ConfirmacionScreen(onIrALogin = {})
    }
}
