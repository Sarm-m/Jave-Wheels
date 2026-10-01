package com.javeriana.javewheels.ui.screens.principal

import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    fun mostrarFiltros() {
        Toast.makeText(context, "Filtros disponibles próximamente", Toast.LENGTH_SHORT).show()
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
                    text = "Salitre → Javeriana",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Mañana, 1 oct. · 7:30–8:00 a. m.",
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
                    text = "${wheelsDisponibles.size} Wheels compatibles",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                OutlinedButton(
                    onClick = { mostrarFiltros() },
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
                        resaltado = filtro == "Modo Buseta",
                        modifier = Modifier.clickable { mostrarFiltros() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            wheelsDisponibles.forEach { wheel ->
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
