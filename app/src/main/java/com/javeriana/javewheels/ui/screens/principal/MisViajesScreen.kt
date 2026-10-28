package com.javeriana.javewheels.ui.screens.principal

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.Rol
import com.javeriana.javewheels.entities.reservasPasajero
import com.javeriana.javewheels.entities.viajesFinalizadosConductor
import com.javeriana.javewheels.entities.wheelsFinalizados
import com.javeriana.javewheels.ui.components.EncabezadoViajes
import com.javeriana.javewheels.ui.components.SelectorRol
import com.javeriana.javewheels.ui.components.TarjetaViajePasajero
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.InicioViewModel

enum class PestanaViajes {
    RESERVAS,
    MIS_WHEELS
}

@Composable
fun MisViajesScreen(
    onVerReserva: () -> Unit = {},
    onVerResumen: (String) -> Unit = {},
    onVerViajeConductor: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: InicioViewModel = viewModel(),
    onQuiereSerConductor: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val esConductor = uiState.rol == Rol.CONDUCTOR
    var pestanaSeleccionada by rememberSaveable(uiState.rol) {
        mutableStateOf(PestanaViajes.RESERVAS)
    }
    val context = LocalContext.current
    val recursos = LocalResources.current
    val mostrandoReservas = pestanaSeleccionada == PestanaViajes.RESERVAS
    val viajes = if (mostrandoReservas) reservasPasajero else wheelsFinalizados

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EncabezadoViajes(
            titulo = if (esConductor || mostrandoReservas) stringResource(R.string.ui_viajes) else stringResource(R.string.ui_mis_wheels),
            modifier = Modifier,
            accion = {
                SelectorRol(uiState.rol, onCambiarRol = {
                    if (!viewModel.cambiarRol()) onQuiereSerConductor()
                })
            }
        )
        SelectorPestanasViajes(
            esConductor = esConductor,
            pestanaSeleccionada = pestanaSeleccionada,
            onSeleccionar = { pestanaSeleccionada = it }
        )
        Text(
            text = if (esConductor) {
                if (mostrandoReservas) stringResource(R.string.ui_tus_viajes_publicados) else stringResource(R.string.ui_viajes_que_ya_condujiste)
            } else if (mostrandoReservas) stringResource(R.string.ui_tus_reservas_activas) else stringResource(R.string.ui_wheels_que_ya_tomaste),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        if (esConductor) {
            val publicados = if (mostrandoReservas) uiState.viajesPublicados else viajesFinalizadosConductor
            if (publicados.isEmpty()) Text(stringResource(R.string.ui_aun_no_tienes_viajes_programados))
            publicados.forEach { viaje ->
                TarjetaViajePasajero(
                    conductor = stringResource(R.string.ui_tu_conductor),
                    subtituloConductor = stringResource(R.string.ui_viaje_publicado_por_ti),
                    ruta = stringResource(R.string.ruta_busqueda, viaje.origen, viaje.destino),
                    horario = stringResource(R.string.fecha_y_hora, viaje.fecha, viaje.hora),
                    cupos = stringResource(R.string.cupos_ocupados_fraccion, viaje.cuposOcupados, viaje.cuposTotales),
                    aporte = viaje.aporte,
                    detalle = stringResource(R.string.cupos_libres, viaje.cuposTotales - viaje.cuposOcupados),
                    estado = viaje.estado,
                    textoBoton = if (mostrandoReservas) stringResource(R.string.ui_ver_viaje) else stringResource(R.string.ui_ver_resumen),
                    onClick = { onVerViajeConductor(viaje.id) }
                )
            }
        } else viajes.forEach { reserva ->
            val wheel = reserva.wheel
            val pendiente = reserva.estado == "Pendiente"
            TarjetaViajePasajero(
                conductor = wheel.conductor,
                ruta = stringResource(R.string.ruta_busqueda, wheel.origen, wheel.destino),
                horario = stringResource(R.string.fecha_y_hora, wheel.fecha, wheel.hora),
                cupos = when {
                    !mostrandoReservas -> stringResource(R.string.ui_1_cupo_utilizado)
                    pendiente -> stringResource(R.string.ui_1_cupo_solicitado)
                    else -> stringResource(R.string.ui_1_cupo_reservado)
                },
                aporte = wheel.aporte,
                detalle = reserva.detalleRecogida,
                estado = reserva.estado,
                resaltado = pendiente,
                textoBoton = when {
                    !mostrandoReservas -> stringResource(R.string.ui_ver_resumen)
                    pendiente -> stringResource(R.string.ui_ver_solicitud)
                    else -> stringResource(R.string.ui_ver_reserva)
                },
                onClick = {
                    when {
                        !mostrandoReservas -> onVerResumen(wheel.id)
                        pendiente -> Toast.makeText(
                            context,
                            recursos.getString(R.string.ui_solicitud_pendiente_de_respuesta_del_conductor),
                            Toast.LENGTH_SHORT
                        ).show()
                        else -> onVerReserva()
                    }
                }
            )
        }
        Text(
            text = if (esConductor) {
                stringResource(R.string.ui_solo_se_muestran_viajes_publicados_por_ti)
            } else if (mostrandoReservas) {
                stringResource(R.string.ui_tambien_puedes_consultar_tu_historial_de_reservas)
            } else {
                stringResource(R.string.ui_solo_se_muestran_tus_viajes_finalizados)
            },
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SelectorPestanasViajes(
    esConductor: Boolean,
    pestanaSeleccionada: PestanaViajes,
    onSeleccionar: (PestanaViajes) -> Unit
) {
    val escala = LocalDensity.current.fontScale
    BoxWithConstraints(Modifier.fillMaxWidth().selectableGroup()) {
        @Composable
        fun OpcionViajes(pestana: PestanaViajes, modificador: Modifier) {
            FilterChip(
                selected = pestanaSeleccionada == pestana,
                onClick = { onSeleccionar(pestana) },
                modifier = modificador.heightIn(min = 48.dp),
                label = {
                    Text(
                        if (esConductor) {
                            if (pestana == PestanaViajes.RESERVAS) stringResource(R.string.ui_programados)
                            else stringResource(R.string.ui_finalizados)
                        } else if (pestana == PestanaViajes.RESERVAS) stringResource(R.string.ui_reservas)
                        else stringResource(R.string.ui_mis_wheels),
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
        if (maxWidth / escala < 260.dp) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PestanaViajes.entries.forEach { OpcionViajes(it, Modifier.fillMaxWidth()) }
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PestanaViajes.entries.forEach { OpcionViajes(it, Modifier.weight(1f)) }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MisViajesScreenPreview() {
    JaveWheelsTheme {
        MisViajesScreen()
    }
}
