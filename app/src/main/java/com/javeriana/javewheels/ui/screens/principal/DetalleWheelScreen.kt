package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.entities.WheelPasajero
import com.javeriana.javewheels.entities.wheelsDisponibles
import com.javeriana.javewheels.ui.components.EtiquetaViaje
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.MapaIlustrativo
import com.javeriana.javewheels.ui.components.DatosDetallePasajero
import com.javeriana.javewheels.ui.components.EncabezadoViajes
import com.javeriana.javewheels.ui.components.PuntoRecogidaPasajero
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun DetalleWheelScreen(
    wheel: WheelPasajero,
    onVolver: () -> Unit,
    onSolicitarCupo: () -> Unit,
    modifier: Modifier = Modifier,
    finalizado: Boolean = false
) {
    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        EncabezadoViajes(if (finalizado) "Resumen del Wheel" else "Detalle del Wheel", onVolver)
        // Mapa local dibujado con componentes de Compose.
        MapaIlustrativo(altura = 230.dp, textoUbicacion = "Recogida", mostrarCentrar = false, textoDestino = wheel.destino)
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (finalizado) EtiquetaViaje("Finalizado")
            DatosDetallePasajero(wheel, if (finalizado) "1 cupo utilizado" else "${wheel.cuposDisponibles} cupos disponibles")
            PuntoRecogidaPasajero(wheel, acordado = finalizado)
            if (!finalizado) BotonPrincipal(texto = "Solicitar cupo", onClick = onSolicitarCupo)
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
