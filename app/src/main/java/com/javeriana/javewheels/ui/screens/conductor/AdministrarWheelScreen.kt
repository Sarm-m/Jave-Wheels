package com.javeriana.javewheels.ui.screens.conductor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.entities.proximoViajeConductor
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.components.*
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
        Text("No se encontró el viaje.", Modifier.padding(24.dp))
        return
    }

    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EncabezadoViajes("Administrar Wheel", onBack, modifier = Modifier)
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Resumen del viaje", style = MaterialTheme.typography.titleMedium)
                    Text("${viaje.origen} → ${viaje.destino}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary)
                    Text("${viaje.fecha} · ${viaje.hora}")
                    Text(viaje.vehiculo)
                    Text("Aporte por pasajero: ${viaje.aporte}")
                    Text(if (viaje.modoBuseta) "Modo Buseta" else "Recogida directa")
                    Text("Recogida: ${viaje.puntoRecogida}")
                }
            }
            Text("Solicitudes pendientes (${viaje.solicitudes.size})",
                style = MaterialTheme.typography.titleMedium)
            if (viaje.solicitudes.isEmpty()) {
                Text("No tienes solicitudes pendientes.", style = MaterialTheme.typography.bodyMedium)
            }
            viaje.solicitudes.forEach { solicitud ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilaConAvatar(solicitud.nombre, "Foto de ${solicitud.nombre}",
                            subtitulo = solicitud.puntoRecogida)
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            BotonSecundario("Rechazar", onClick = {
                                viewModel.rechazarSolicitud(idViaje, solicitud.id)
                            }, modifier = Modifier.weight(1f))
                            Button(
                                onClick = {
                                    if (viewModel.aceptarSolicitud(idViaje, solicitud.id)) {
                                        pasajeroAceptado = solicitud.nombre
                                    }
                                },
                                enabled = viaje.cuposOcupados < viaje.cuposTotales,
                                modifier = Modifier.weight(1f).height(52.dp),
                                shape = RoundedCornerShape(14.dp)
                            ) { Text("Aceptar") }
                        }
                    }
                }
            }
            if (viaje.cuposOcupados >= viaje.cuposTotales && viaje.solicitudes.isNotEmpty()) {
                Text("Ya no hay cupos disponibles.", color = MaterialTheme.colorScheme.error)
            }
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Pasajeros confirmados", style = MaterialTheme.typography.titleMedium)
                    Text("${viaje.cuposOcupados} de ${viaje.cuposTotales} cupos ocupados")
                    LinearProgressIndicator(
                        progress = { viaje.cuposOcupados.toFloat() / viaje.cuposTotales.coerceAtLeast(1) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (viaje.pasajerosConfirmados.isEmpty()) Text("Aún no hay pasajeros confirmados.")
                    viaje.pasajerosConfirmados.forEach { pasajero ->
                        FilaConAvatar(pasajero.nombre, "Foto de ${pasajero.nombre}",
                            subtitulo = "Recogida: ${pasajero.puntoRecogida}")
                    }
                }
            }
            BotonPrincipal("Iniciar viaje", onClick = onIniciarViaje)
            BotonTexto("Cancelar Wheel", onClick = { confirmarCancelacion = true })
        }
        if (mostrarBarraNavegacion) BarraNavegacion(Pestana.MisViajes, onSeleccionarPestana)
    }
    if (confirmarCancelacion) {
        AlertDialog(
            onDismissRequest = { confirmarCancelacion = false },
            title = { Text("Cancelar Wheel") },
            text = { Text("¿Quieres cancelar este viaje? Se quitará de tus viajes programados.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmarCancelacion = false
                    onCancelarWheel()
                }) { Text("Sí, cancelar") }
            },
            dismissButton = {
                TextButton(onClick = { confirmarCancelacion = false }) { Text("Mantener viaje") }
            }
        )
    }
    pasajeroAceptado?.let { nombre ->
        AlertDialog(
            onDismissRequest = { pasajeroAceptado = null },
            title = { Text("Pasajero aceptado") },
            text = { Text("$nombre se agregó a los pasajeros confirmados.\n${viaje.cuposOcupados} de ${viaje.cuposTotales} cupos ocupados.") },
            confirmButton = {
                TextButton(onClick = { pasajeroAceptado = null }) { Text("Listo") }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AdministrarWheelScreenPreview() {
    JaveWheelsTheme { AdministrarWheelScreen() }
}
