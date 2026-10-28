package com.javeriana.javewheels.ui.screens.acceso

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
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
            .fillMaxSize().imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MarcaJaveWheels(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(24.dp))
        IconoConfirmacion()
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.ui_todo_listo),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextoAyuda(
            texto = stringResource(R.string.ui_ya_puedes_iniciar_sesion_con_tu_correo_y_contrasena),
            centrado = true
        )
        Spacer(modifier = Modifier.height(40.dp))
        BotonPrincipal(texto = stringResource(R.string.ui_ir_a_iniciar_sesion), onClick = onIrALogin)
        Spacer(modifier = Modifier.height(24.dp))

        TextoAyuda(texto = stringResource(R.string.ui_tu_proximo_wheel_te_espera), centrado = true)
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConfirmacionScreenPreview() {
    JaveWheelsTheme {
        ConfirmacionScreen(onIrALogin = {})
    }
}
