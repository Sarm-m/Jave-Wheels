package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.IconoConfirmacion
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun VehiculoActualizadoScreen(
    onIrAlMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        MarcaJaveWheels()
        Spacer(modifier = Modifier.weight(1f))
        IconoConfirmacion(
            descripcion = "Vehículo actualizado",
            conDiscoAmarillo = true,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "¡Tu vehículo fue actualizado correctamente!",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.weight(1f))
        BotonPrincipal(texto = "Ir al menú principal", onClick = onIrAlMenu)
    }
}

@Preview(showSystemUi = true)
@Composable
fun VehiculoActualizadoScreenPreview() {
    JaveWheelsTheme {
        VehiculoActualizadoScreen(onIrAlMenu = {})
    }
}