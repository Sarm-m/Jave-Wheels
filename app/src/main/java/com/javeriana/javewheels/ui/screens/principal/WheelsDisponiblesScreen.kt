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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.entities.filtrarWheels
import com.javeriana.javewheels.entities.wheelsDisponibles
import com.javeriana.javewheels.ui.components.EncabezadoViajes
import com.javeriana.javewheels.ui.components.EtiquetaViaje
import com.javeriana.javewheels.ui.components.PanelInferior
import com.javeriana.javewheels.ui.components.TarjetaViajePasajero
import com.javeriana.javewheels.ui.theme.JWCelesteSuave
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
    val resultados = filtrarWheels(wheelsDisponibles, origen, destino, distanciaMaxima, horario, cuposMinimos, aporteMaximo, soloBuseta, desdeMillis)
    fun restablecer() {
        distanciaMaxima = 1000; horario = 0; cuposMinimos = 1; aporteMaximo = 6000; soloBuseta = false
    }
    if (filtrosAbiertos) {
        AlertDialog(
            onDismissRequest = { filtrosAbiertos = false },
            title = { Text("Filtrar Wheels") },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Cercanía: hasta $distanciaMaxima m")
                    Slider(value = distanciaMaxima.toFloat(), onValueChange = { distanciaMaxima = it.toInt() }, valueRange = 100f..1000f, steps = 8)
                    Text("Horario")
                    listOf("Todos los horarios", "Antes de las 8:00", "De 8:00 a 12:00", "Después de las 12:00").forEachIndexed { indice, texto ->
                        FilterChip(selected = horario == indice, onClick = { horario = indice }, label = { Text(texto) })
                    }
                    Text("Cupos mínimos")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        (1..3).forEach { cupos ->
                            FilterChip(selected = cuposMinimos == cupos, onClick = { cuposMinimos = cupos }, label = { Text("$cupos") })
                        }
                    }
                    Text("Aporte máximo: $$aporteMaximo")
                    Slider(value = aporteMaximo.toFloat(), onValueChange = { aporteMaximo = it.toInt() }, valueRange = 2000f..6000f, steps = 3)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = soloBuseta, onCheckedChange = { soloBuseta = it })
                        Text("Solo Modo Buseta", modifier = Modifier.clickable { soloBuseta = !soloBuseta })
                    }
                    Text("${resultados.size} resultados")
                }
            },
            confirmButton = { TextButton(onClick = { filtrosAbiertos = false }) { Text("Ver resultados") } },
            dismissButton = { TextButton(onClick = { restablecer() }) { Text("Restablecer") } }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoViajes(titulo = "Wheels disponibles", onVolver = onVolver)

        PanelInferior {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(JWCelesteSuave)
                    .padding(14.dp)
            ) {
                Text(
                    text = "$origen → ${destino.ifBlank { "Todos los destinos" }}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = desdeMillis?.let {
                        "${if (programado) "Programado" else "Ahora"} · ${com.javeriana.javewheels.entities.fechaBusqueda(it)} · ${com.javeriana.javewheels.entities.horaBusqueda(it)}\nSalidas durante la siguiente hora · datos de demostración"
                    } ?: "Recorridos locales disponibles",
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
                    text = "${resultados.size} Wheels compatibles",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                OutlinedButton(
                    onClick = { filtrosAbiertos = true },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Filtros")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("Cercanía", "Horario", "Cupos", "Aporte", "Modo Buseta").forEach { filtro ->
                    EtiquetaViaje(
                        texto = filtro,
                        resaltado = when (filtro) {
                            "Cercanía" -> distanciaMaxima < 1000
                            "Horario" -> horario != 0
                            "Cupos" -> cuposMinimos > 1
                            "Aporte" -> aporteMaximo < 6000
                            else -> soloBuseta
                        },
                        modifier = Modifier.clickable { filtrosAbiertos = true }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            if (resultados.isEmpty()) {
                Text("No hay Wheels para esta ruta y estos filtros.")
                TextButton(onClick = { restablecer() }) { Text("Limpiar filtros") }
            }
            resultados.forEach { wheel ->
                TarjetaViajePasajero(
                    conductor = wheel.conductor,
                    ruta = "${wheel.origen} → ${wheel.destino}",
                    horario = "${wheel.fecha} · ${wheel.hora}",
                    cupos = "${wheel.cuposDisponibles} cupos disponibles",
                    aporte = wheel.aporte,
                    detalle = wheel.compatibilidad,
                    estado = if (wheel.modoBuseta) "Modo Buseta" else "Directo",
                    resaltado = wheel.modoBuseta,
                    textoBoton = "Ver Wheel",
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
