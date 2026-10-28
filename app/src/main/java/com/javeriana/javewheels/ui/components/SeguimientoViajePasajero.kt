package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.WheelPasajero
import com.javeriana.javewheels.navigation.Pestana

@Composable
fun SeguimientoViajePasajero(
    wheel: WheelPasajero,
    modoBuseta: Boolean,
    onVolver: () -> Unit,
    onCentrar: () -> Unit,
    onCompartir: (() -> Unit)?,
    mostrarBarraNavegacion: Boolean,
    onSeleccionarPestana: (Pestana) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        // El mapa conserva espacio propio: el panel puede desplazarse con letra grande.
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            EncabezadoViajes(if (modoBuseta) stringResource(R.string.ui_modo_buseta) else stringResource(R.string.ui_tu_viaje_actual), onVolver)
            MapaJaveWheels(altura = 230.dp, alCentrar = { onCentrar() })
            Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(stringResource(R.string.mapa_viaje_sin_seguimiento),
                    style = MaterialTheme.typography.bodyMedium)
                DatosDetallePasajero(wheel, stringResource(R.string.ui_1_cupo_reservado))
                if (modoBuseta) PuntoRecogidaPasajero(wheel, acordado = true)
                if (onCompartir != null) BotonPrincipal(stringResource(R.string.ui_compartir_viaje), onCompartir)
            }
        }
        if (mostrarBarraNavegacion) BarraNavegacion(Pestana.MisViajes, onSeleccionarPestana)
    }
}
