package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.WheelPasajero

/** Encabezado del flujo de Wheels y reservas. */
@Composable
fun EncabezadoViajes(
    titulo: String,
    onVolver: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    accion: @Composable () -> Unit = {}
) {
    FilaAdaptable(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        principal = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically) {
                LogoJaveWheels()
                Text(titulo, style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                if (onVolver != null) BotonVolver(onClick = onVolver)
            }
        },
        secundario = accion
    )
}

@Composable
fun EtiquetaViaje(texto: String, resaltado: Boolean = false, modifier: Modifier = Modifier) {
    Text(
        text = texto,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
        modifier = modifier
            .clip(RoundedCornerShape(11.dp))
            .background(if (resaltado) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

@Composable
fun DatosConductorPasajero(nombre: String, subtitulo: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarUsuario(
            foto = null,
            descripcion = stringResource(R.string.ui_avatar),
            modifier = Modifier.size(44.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(nombre, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
            Text(subtitulo, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

/** Tarjeta compartida por resultados, reservas e historial. */
@Composable
fun TarjetaViajePasajero(
    conductor: String,
    ruta: String,
    horario: String,
    cupos: String,
    aporte: String,
    detalle: String,
    estado: String,
    resaltado: Boolean = false,
    textoBoton: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtituloConductor: String = stringResource(R.string.ui_conductor_comunidad_javeriana)
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            DatosConductorPasajero(conductor, subtituloConductor)
            Text(ruta, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Text(horario, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(cupos, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                Text(aporte, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(detalle, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                EtiquetaViaje(estado, resaltado)
            }
            BotonSecundario(
                texto = textoBoton,
                onClick = onClick,
                modifier = Modifier.padding(top = 2.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun DatosDetallePasajero(wheel: WheelPasajero, cupos: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(stringResource(R.string.ruta_busqueda, wheel.origen, wheel.destino), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        Text(stringResource(R.string.fecha_y_hora_salida, wheel.fecha, wheel.hora), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        DatosConductorPasajero(wheel.conductor, stringResource(R.string.calificacion_comunidad, wheel.calificacion))
        InformacionVehiculo(wheel.vehiculo)
        FlowRow(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceContainerLow).padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(cupos, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center)
            Text(stringResource(R.string.ui_aporte_1_s, wheel.aporte), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun PuntoRecogidaPasajero(wheel: WheelPasajero, acordado: Boolean = false) {
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.surfaceContainerLow).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val titulo = if (wheel.modoBuseta) {
            if (acordado) stringResource(R.string.ui_modo_buseta_punto_acordado_de_recogida) else stringResource(R.string.ui_modo_buseta_punto_sugerido_de_recogida)
        } else stringResource(R.string.ui_recogida_directa_punto_de_encuentro)
        Text(titulo, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
        Text(wheel.puntoRecogida, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        Text(stringResource(R.string.distancia_y_tiempo_a_pie, wheel.distancia, wheel.tiempoAPie), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(if (acordado) stringResource(R.string.ui_hora_estimada_1_s, wheel.horaRecogida) else stringResource(R.string.ui_paso_aproximado_1_s, wheel.horaRecogida), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
