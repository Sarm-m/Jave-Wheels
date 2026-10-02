package com.javeriana.javewheels.ui.screens.principal

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.entities.Rol
import com.javeriana.javewheels.entities.viajesProgramadosConductor
import com.javeriana.javewheels.entities.viajesFinalizadosConductor
import com.javeriana.javewheels.entities.reservasPasajero
import com.javeriana.javewheels.entities.wheelsFinalizados
import com.javeriana.javewheels.viewmodels.InicioViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.ui.components.SelectorRol
import com.javeriana.javewheels.ui.components.EncabezadoViajes
import com.javeriana.javewheels.ui.components.TarjetaViajePasajero
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

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
            titulo = if (esConductor || mostrandoReservas) "Viajes" else "Mis Wheels",
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
                if (mostrandoReservas) "Tus viajes publicados" else "Viajes que ya condujiste"
            } else if (mostrandoReservas) "Tus reservas activas" else "Wheels que ya tomaste",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        if (esConductor) {
            val publicados = if (mostrandoReservas) viajesProgramadosConductor else viajesFinalizadosConductor
            publicados.forEach { viaje ->
                TarjetaViajePasajero(
                    conductor = "Tú · Conductor",
                    subtituloConductor = "Viaje publicado por ti",
                    ruta = "${viaje.origen} → ${viaje.destino}",
                    horario = "${viaje.fecha} · ${viaje.hora}",
                    cupos = "${viaje.cuposOcupados}/${viaje.cuposTotales} cupos ocupados",
                    aporte = viaje.aporte,
                    detalle = "${viaje.cuposTotales - viaje.cuposOcupados} cupos libres",
                    estado = viaje.estado,
                    textoBoton = if (mostrandoReservas) "Ver viaje" else "Ver resumen",
                    onClick = { onVerViajeConductor(viaje.id) }
                )
            }
        } else viajes.forEach { reserva ->
            val wheel = reserva.wheel
            val pendiente = reserva.estado == "Pendiente"
            TarjetaViajePasajero(
                conductor = wheel.conductor,
                ruta = "${wheel.origen} → ${wheel.destino}",
                horario = "${wheel.fecha} · ${wheel.hora}",
                cupos = when {
                    !mostrandoReservas -> "1 cupo utilizado"
                    pendiente -> "1 cupo solicitado"
                    else -> "1 cupo reservado"
                },
                aporte = wheel.aporte,
                detalle = reserva.detalleRecogida,
                estado = reserva.estado,
                resaltado = pendiente,
                textoBoton = when {
                    !mostrandoReservas -> "Ver resumen"
                    pendiente -> "Ver solicitud"
                    else -> "Ver reserva"
                },
                onClick = {
                    when {
                        !mostrandoReservas -> onVerResumen(wheel.id)
                        pendiente -> Toast.makeText(
                            context,
                            "Solicitud pendiente de respuesta del conductor.",
                            Toast.LENGTH_SHORT
                        ).show()
                        else -> onVerReserva()
                    }
                }
            )
        }
        Text(
            text = if (esConductor) {
                "Solo se muestran viajes publicados por ti."
            } else if (mostrandoReservas) {
                "También puedes consultar tu historial de reservas."
            } else {
                "Solo se muestran tus viajes finalizados."
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PestanaViajes.entries.forEach { pestana ->
            val seleccionada = pestanaSeleccionada == pestana
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        if (seleccionada) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondaryContainer
                    )
                    .selectable(
                        selected = seleccionada,
                        role = Role.Tab,
                        onClick = { onSeleccionar(pestana) }
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (esConductor) {
                        if (pestana == PestanaViajes.RESERVAS) "Programados" else "Finalizados"
                    } else if (pestana == PestanaViajes.RESERVAS) "Reservas" else "Mis Wheels",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (seleccionada) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.primary
                )
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
