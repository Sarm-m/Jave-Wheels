package com.javeriana.javewheels.ui.screens.conductor

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
import com.javeriana.javewheels.ui.components.BotonSecundario
import com.javeriana.javewheels.ui.components.IconoConfirmacion
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// "Conductor - Registro completado".
// ============================================================

@Composable
fun ConductorCompletadoScreen(
    onIrInicioConductor: () -> Unit,
    onContinuarPasajero: () -> Unit,
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
            text = "¡Tu perfil de conductor\nestá listo!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextoAyuda(
            texto = "Ya puedes publicar Wheels. Puedes cambiar\nentre pasajero y conductor desde Inicio.",
            centrado = true
        )
        Spacer(modifier = Modifier.height(40.dp))
        BotonPrincipal(texto = "Ir al inicio del conductor", onClick = onIrInicioConductor)
        Spacer(modifier = Modifier.height(16.dp))
        BotonSecundario(texto = "Continuar como pasajero", onClick = onContinuarPasajero)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConductorCompletadoScreenPreview() {
    JaveWheelsTheme {
        ConductorCompletadoScreen(onIrInicioConductor = {}, onContinuarPasajero = {})
    }
}
