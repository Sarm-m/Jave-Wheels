package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.Viaje
import com.javeriana.javewheels.entities.formatoPesos
import com.javeriana.javewheels.ui.components.*
import com.javeriana.javewheels.ui.components.InformacionVehiculo

@Composable
fun DetalleViajeConductorScreen(
    viaje: Viaje,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier,
    onAdministrar: (() -> Unit)? = null
) {
    val finalizado = viaje.estado == "Finalizado"
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        EncabezadoViajes(if (finalizado) stringResource(R.string.ui_resumen_del_viaje) else stringResource(R.string.ui_detalle_del_viaje), onVolver)
        MapaIlustrativo(altura = 230.dp, textoUbicacion = viaje.origen, textoDestino = viaje.destino, mostrarCentrar = false)
        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            EtiquetaViaje(viaje.estado)
            Text(stringResource(R.string.ruta_busqueda, viaje.origen, viaje.destino), style = MaterialTheme.typography.headlineSmall)
            Text(stringResource(R.string.fecha_y_hora_salida, viaje.fecha, viaje.hora))
            DatosConductorPasajero(stringResource(R.string.ui_tu_conductor), stringResource(R.string.ui_viaje_publicado_por_ti))
            InformacionVehiculo(viaje.vehiculo)
            Text(stringResource(R.string.cupos_ocupados_fraccion, viaje.cuposOcupados, viaje.cuposTotales))
            if (!finalizado) Text(stringResource(R.string.cupos_disponibles, viaje.cuposTotales - viaje.cuposOcupados))
            Text(stringResource(R.string.ui_aporte_por_pasajero_1_s, viaje.aporte))
            Text(stringResource(R.string.ui_punto_de_encuentro), style = MaterialTheme.typography.titleMedium)
            Text(viaje.puntoRecogida)
            if (finalizado) {
                Text(stringResource(R.string.ui_total_recaudado), style = MaterialTheme.typography.titleMedium)
                Text(formatoPesos(viaje.totalRecaudado), style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary)
                Text(stringResource(R.string.recaudo_pasajeros_aporte, viaje.cuposOcupados, viaje.aporte),
                    style = MaterialTheme.typography.bodyMedium)
                Text(stringResource(R.string.ui_viaje_finalizado_este_resumen_conserva_los_datos_del_recorrido))
            }
            if (!finalizado && onAdministrar != null) {
                BotonPrincipal(stringResource(R.string.ui_administrar_wheel), onClick = onAdministrar)
            }
        }
    }
}
