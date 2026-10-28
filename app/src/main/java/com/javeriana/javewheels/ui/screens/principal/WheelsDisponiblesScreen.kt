package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.filtrarWheels
import com.javeriana.javewheels.entities.wheelsDisponibles
import com.javeriana.javewheels.ui.components.EncabezadoViajes
import com.javeriana.javewheels.ui.components.EtiquetaViaje
import com.javeriana.javewheels.ui.components.PanelInferior
import com.javeriana.javewheels.ui.components.TarjetaViajePasajero
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

/** Resultados de la búsqueda de Wheels del pasajero. */
@Composable
fun WheelsDisponiblesScreen(
    onVerWheel: (String) -> Unit,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier,
    origen: String = "Mi ubicación",
    destino: String = "",
    desdeMillis: Long? = null,
    programado: Boolean = false
) {
    var filtrosAbiertos by rememberSaveable { mutableStateOf(false) }
    var distanciaMaxima by rememberSaveable { mutableStateOf(1000) }
    var horario by rememberSaveable { mutableStateOf(0) }
    var cuposMinimos by rememberSaveable { mutableStateOf(1) }
    var aporteMaximo by rememberSaveable { mutableStateOf(6000) }
    var soloBuseta by rememberSaveable { mutableStateOf(false) }
    val tipoBusqueda = if (programado) stringResource(R.string.ui_programado) else stringResource(R.string.ui_ahora)
    val resultados = filtrarWheels(wheelsDisponibles, origen, destino, distanciaMaxima, horario, cuposMinimos, aporteMaximo, soloBuseta, desdeMillis)
    fun restablecer() {
        distanciaMaxima = 1000; horario = 0; cuposMinimos = 1; aporteMaximo = 6000; soloBuseta = false
    }
    if (filtrosAbiertos) {
        AlertDialog(
            onDismissRequest = { filtrosAbiertos = false },
            title = { Text(stringResource(R.string.ui_filtrar_wheels)) },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(stringResource(R.string.ui_cercania_hasta_1_s_m, distanciaMaxima))
                    Slider(value = distanciaMaxima.toFloat(), onValueChange = { distanciaMaxima = it.toInt() }, valueRange = 100f..1000f, steps = 8)
                    Text(stringResource(R.string.ui_horario))
                    listOf(stringResource(R.string.ui_todos_los_horarios), stringResource(R.string.ui_antes_de_las_8_00), stringResource(R.string.ui_de_8_00_a_12_00), stringResource(R.string.ui_despues_de_las_12_00)).forEachIndexed { indice, texto ->
                        FilterChip(selected = horario == indice, onClick = { horario = indice }, label = { Text(texto) })
                    }
                    Text(stringResource(R.string.ui_cupos_minimos))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        (1..3).forEach { cupos ->
                            FilterChip(selected = cuposMinimos == cupos, onClick = { cuposMinimos = cupos }, label = { Text(stringResource(R.string.cantidad, cupos)) })
                        }
                    }
                    Text(stringResource(R.string.ui_aporte_maximo_1_s, aporteMaximo))
                    Slider(value = aporteMaximo.toFloat(), onValueChange = { aporteMaximo = it.toInt() }, valueRange = 2000f..6000f, steps = 3)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = soloBuseta, onCheckedChange = { soloBuseta = it })
                        Text(stringResource(R.string.ui_solo_modo_buseta), modifier = Modifier.clickable { soloBuseta = !soloBuseta })
                    }
                    Text(stringResource(R.string.cantidad_resultados, resultados.size))
                }
            },
            confirmButton = { TextButton(onClick = { filtrosAbiertos = false }) { Text(stringResource(R.string.ui_ver_resultados)) } },
            dismissButton = { TextButton(onClick = { restablecer() }) { Text(stringResource(R.string.ui_restablecer)) } }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoViajes(titulo = stringResource(R.string.ui_wheels_disponibles), onVolver = onVolver)

        PanelInferior {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(14.dp)
            ) {
                Text(
                    text = stringResource(R.string.ruta_busqueda, origen, destino.ifBlank { stringResource(R.string.todos_destinos) }),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = desdeMillis?.let {
                        stringResource(R.string.resumen_ventana_busqueda, tipoBusqueda, com.javeriana.javewheels.entities.fechaBusqueda(it), com.javeriana.javewheels.entities.horaBusqueda(it))
                    } ?: stringResource(R.string.ui_recorridos_locales_disponibles),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.cantidad_wheels_compatibles, resultados.size),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                OutlinedButton(
                    onClick = { filtrosAbiertos = true },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(R.string.ui_filtros))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(stringResource(R.string.ui_cercania), stringResource(R.string.ui_horario), stringResource(R.string.ui_cupos), stringResource(R.string.ui_aporte), stringResource(R.string.ui_modo_buseta)).forEach { filtro ->
                    EtiquetaViaje(
                        texto = filtro,
                        resaltado = when (filtro) {
                            stringResource(R.string.ui_cercania) -> distanciaMaxima < 1000
                            stringResource(R.string.ui_horario) -> horario != 0
                            stringResource(R.string.ui_cupos) -> cuposMinimos > 1
                            stringResource(R.string.ui_aporte) -> aporteMaximo < 6000
                            else -> soloBuseta
                        },
                        modifier = Modifier.clickable { filtrosAbiertos = true }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            if (resultados.isEmpty()) {
                Text(stringResource(R.string.ui_no_hay_wheels_para_esta_ruta_y_estos_filtros))
                TextButton(onClick = { restablecer() }) { Text(stringResource(R.string.ui_limpiar_filtros)) }
            }
            resultados.forEach { wheel ->
                TarjetaViajePasajero(
                    conductor = wheel.conductor,
                    ruta = stringResource(R.string.ruta_busqueda, wheel.origen, wheel.destino),
                    horario = stringResource(R.string.fecha_y_hora, wheel.fecha, wheel.hora),
                    cupos = stringResource(R.string.cupos_disponibles, wheel.cuposDisponibles),
                    aporte = wheel.aporte,
                    detalle = wheel.compatibilidad,
                    estado = if (wheel.modoBuseta) stringResource(R.string.ui_modo_buseta) else stringResource(R.string.ui_directo),
                    resaltado = wheel.modoBuseta,
                    textoBoton = stringResource(R.string.ui_ver_wheel),
                    onClick = { onVerWheel(wheel.id) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WheelsDisponiblesScreenPreview() {
    JaveWheelsTheme {
        WheelsDisponiblesScreen(onVerWheel = {}, onVolver = {})
    }
}
