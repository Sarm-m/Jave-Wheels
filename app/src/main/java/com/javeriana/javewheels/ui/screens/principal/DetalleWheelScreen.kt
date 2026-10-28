package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.WheelPasajero
import com.javeriana.javewheels.entities.wheelsDisponibles
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.DatosDetallePasajero
import com.javeriana.javewheels.ui.components.EncabezadoViajes
import com.javeriana.javewheels.ui.components.EtiquetaViaje
import com.javeriana.javewheels.ui.components.MapaJaveWheels
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
        EncabezadoViajes(if (finalizado) stringResource(R.string.ui_resumen_del_wheel) else stringResource(R.string.ui_detalle_del_wheel), onVolver)
        // Sin coordenadas del viaje se utiliza la referencia geográfica inicial.
        MapaJaveWheels(altura = 230.dp, mostrarCentrar = false)
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (finalizado) EtiquetaViaje(stringResource(R.string.ui_finalizado))
            DatosDetallePasajero(wheel, if (finalizado) stringResource(R.string.ui_1_cupo_utilizado) else stringResource(R.string.cupos_disponibles, wheel.cuposDisponibles))
            PuntoRecogidaPasajero(wheel, acordado = finalizado)
            if (!finalizado) BotonPrincipal(texto = stringResource(R.string.ui_solicitar_cupo), onClick = onSolicitarCupo)
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
