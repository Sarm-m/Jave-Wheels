package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmacionActualizacion(
    titulo: String,
    descripcion: String,
    textoBoton: String,
    onContinuar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)) {
        MarcaJaveWheels()
        IconoConfirmacion(descripcion = descripcion, conDiscoAmarillo = true,
            modifier = Modifier.align(Alignment.CenterHorizontally))
        Text(titulo, style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary)
        BotonPrincipal(textoBoton, onContinuar)
    }
}
