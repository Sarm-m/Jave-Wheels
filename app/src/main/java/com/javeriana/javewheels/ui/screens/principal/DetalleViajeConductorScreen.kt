package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.entities.Viaje
import com.javeriana.javewheels.entities.formatoPesos
import com.javeriana.javewheels.ui.components.*

@Composable
fun DetalleViajeConductorScreen(
    viaje: Viaje,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier,
    onAdministrar: (() -> Unit)? = null
) {
    val finalizado = viaje.estado == "Finalizado"
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        EncabezadoViajes(if (finalizado) "Resumen del viaje" else "Detalle del viaje", onVolver)
        MapaIlustrativo(altura = 230.dp, textoUbicacion = viaje.origen, textoDestino = viaje.destino, mostrarCentrar = false)
        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            EtiquetaViaje(viaje.estado)
            Text("${viaje.origen} → ${viaje.destino}", style = MaterialTheme.typography.headlineSmall)
            Text("${viaje.fecha} · Salida: ${viaje.hora}")
            DatosConductorPasajero("Tú · Conductor", "Viaje publicado por ti")
            Text(viaje.vehiculo)
            Text("${viaje.cuposOcupados}/${viaje.cuposTotales} cupos ocupados")
            if (!finalizado) Text("${viaje.cuposTotales - viaje.cuposOcupados} cupos disponibles")
            Text("Aporte por pasajero: ${viaje.aporte}")
            Text("Punto de encuentro", style = MaterialTheme.typography.titleMedium)
            Text(viaje.puntoRecogida)
            if (finalizado) {
                Text("Total recaudado", style = MaterialTheme.typography.titleMedium)
                Text(formatoPesos(viaje.totalRecaudado), style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary)
                Text("${viaje.cuposOcupados} pasajeros × ${viaje.aporte}",
                    style = MaterialTheme.typography.bodyMedium)
                Text("Viaje finalizado. Este resumen conserva los datos del recorrido.")
            }
            if (!finalizado && onAdministrar != null) {
                BotonPrincipal("Administrar Wheel", onClick = onAdministrar)
            }
        }
    }
}
