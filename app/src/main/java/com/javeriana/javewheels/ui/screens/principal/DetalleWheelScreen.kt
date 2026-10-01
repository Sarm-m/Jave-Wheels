package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.WheelPasajero
import com.javeriana.javewheels.entities.wheelsDisponibles
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.DatosDetallePasajero
import com.javeriana.javewheels.ui.components.EncabezadoViajes
import com.javeriana.javewheels.ui.components.PuntoRecogidaPasajero
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun DetalleWheelScreen(
    wheel: WheelPasajero,
    onVolver: () -> Unit,
    onSolicitarCupo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        EncabezadoViajes("Detalle del Wheel", onVolver)
        // La ilustración es un recurso local; no consulta ubicaciones.
        Image(
            painter = painterResource(R.drawable.mapa_recorrido_wheel),
            contentDescription = "Mapa ilustrativo del punto de recogida y la Javeriana",
            modifier = Modifier.fillMaxWidth().aspectRatio(390f / 230f)
        )
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DatosDetallePasajero(wheel, "${wheel.cuposDisponibles} cupos disponibles")
            PuntoRecogidaPasajero(wheel)
            BotonPrincipal(texto = "Solicitar cupo", onClick = onSolicitarCupo)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleWheelScreenPreview() {
    JaveWheelsTheme {
        DetalleWheelScreen(wheelsDisponibles.last(), onVolver = {}, onSolicitarCupo = {})
    }
}
