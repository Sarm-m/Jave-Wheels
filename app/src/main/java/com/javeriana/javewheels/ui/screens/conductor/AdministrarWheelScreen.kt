package com.javeriana.javewheels.ui.screens.conductor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.proximoViajeConductor
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.components.*
import com.javeriana.javewheels.ui.components.InformacionVehiculo
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.InicioViewModel

@Composable
fun AdministrarWheelScreen(
    onBack: () -> Unit = {},
    onIniciarViaje: () -> Unit = {},
    onCancelarWheel: () -> Unit = {},
    onSeleccionarPestana: (Pestana) -> Unit = {},
    mostrarBarraNavegacion: Boolean = true,
    idViaje: String = proximoViajeConductor.id,
    viewModel: InicioViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsState()
    val viaje = estado.viajesPublicados.firstOrNull { it.id == idViaje }
    var pasajeroAceptado by rememberSaveable(idViaje) { mutableStateOf<String?>(null) }
    var confirmarCancelacion by rememberSaveable(idViaje) { mutableStateOf(false) }
    if (viaje == null) {
        Text(stringResource(R.string.ui_no_se_encontro_el_viaje), Modifier.padding(24.dp))
        return
    }

    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EncabezadoViajes(stringResource(R.string.ui_administrar_wheel), onBack, modifier = Modifier)
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(stringResource(R.string.ui_resumen_del_viaje), style = MaterialTheme.typography.titleMedium)
                    Text(stringResource(R.string.ruta_busqueda, viaje.origen, viaje.destino),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary)
                    Text(stringResource(R.string.fecha_y_hora, viaje.fecha, viaje.hora))
                    InformacionVehiculo(viaje.vehiculo)
                    Text(stringResource(R.string.ui_aporte_por_pasajero_1_s, viaje.aporte))
                    Text(if (viaje.modoBuseta) stringResource(R.string.ui_modo_buseta) else stringResource(R.string.ui_recogida_directa))
                    Text(stringResource(R.string.ui_recogida_1_s, viaje.puntoRecogida))
                }
            }
            Text(stringResource(R.string.ui_solicitudes_pendientes_1_s, viaje.solicitudes.size),
                style = MaterialTheme.typography.titleMedium)
            if (viaje.solicitudes.isEmpty()) {
                Text(stringResource(R.string.ui_no_tienes_solicitudes_pendientes), style = MaterialTheme.typography.bodyMedium)
            }
            viaje.solicitudes.forEach { solicitud ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilaConAvatar(solicitud.nombre, stringResource(R.string.ui_foto_de_1_s, solicitud.nombre),
                            subtitulo = solicitud.puntoRecogida)
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            BotonSecundario(stringResource(R.string.ui_rechazar), onClick = {
                                viewModel.rechazarSolicitud(idViaje, solicitud.id)
                            }, modifier = Modifier.fillMaxWidth())
                            Button(
                                onClick = {
                                    if (viewModel.aceptarSolicitud(idViaje, solicitud.id)) {
                                        pasajeroAceptado = solicitud.nombre
                                    }
                                },
                                enabled = viaje.cuposOcupados < viaje.cuposTotales,
                                modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                                shape = RoundedCornerShape(14.dp)
                            ) { Text(stringResource(R.string.ui_aceptar)) }
                        }
                    }
                }
            }
            if (viaje.cuposOcupados >= viaje.cuposTotales && viaje.solicitudes.isNotEmpty()) {
                Text(stringResource(R.string.ui_ya_no_hay_cupos_disponibles), color = MaterialTheme.colorScheme.error)
            }
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(stringResource(R.string.ui_pasajeros_confirmados), style = MaterialTheme.typography.titleMedium)
                    Text(stringResource(R.string.cupos_ocupados_descripcion, viaje.cuposOcupados, viaje.cuposTotales))
                    LinearProgressIndicator(
                        progress = { viaje.cuposOcupados.toFloat() / viaje.cuposTotales.coerceAtLeast(1) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (viaje.pasajerosConfirmados.isEmpty()) Text(stringResource(R.string.ui_aun_no_hay_pasajeros_confirmados))
                    viaje.pasajerosConfirmados.forEach { pasajero ->
                        FilaConAvatar(pasajero.nombre, stringResource(R.string.ui_foto_de_1_s, pasajero.nombre),
                            subtitulo = stringResource(R.string.ui_recogida_1_s, pasajero.puntoRecogida))
                    }
                }
            }
            BotonPrincipal(stringResource(R.string.ui_iniciar_viaje), onClick = onIniciarViaje)
            BotonTexto(stringResource(R.string.ui_cancelar_wheel), onClick = { confirmarCancelacion = true })
        }
        if (mostrarBarraNavegacion) BarraNavegacion(Pestana.MisViajes, onSeleccionarPestana)
    }
    if (confirmarCancelacion) {
        AlertDialog(
            onDismissRequest = { confirmarCancelacion = false },
            title = { Text(stringResource(R.string.ui_cancelar_wheel)) },
            text = { Text(stringResource(R.string.ui_quieres_cancelar_este_viaje_se_quitara_de_tus_viajes_programados)) },
            confirmButton = {
                TextButton(onClick = {
                    confirmarCancelacion = false
                    onCancelarWheel()
                }) { Text(stringResource(R.string.ui_si_cancelar)) }
            },
            dismissButton = {
                TextButton(onClick = { confirmarCancelacion = false }) { Text(stringResource(R.string.ui_mantener_viaje)) }
            }
        )
    }
    pasajeroAceptado?.let { nombre ->
        AlertDialog(
            onDismissRequest = { pasajeroAceptado = null },
            title = { Text(stringResource(R.string.ui_pasajero_aceptado)) },
            text = { Text(stringResource(R.string.pasajero_aceptado_confirmacion, nombre, viaje.cuposOcupados, viaje.cuposTotales)) },
            confirmButton = {
                TextButton(onClick = { pasajeroAceptado = null }) { Text(stringResource(R.string.listo)) }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AdministrarWheelScreenPreview() {
    JaveWheelsTheme { AdministrarWheelScreen() }
}
