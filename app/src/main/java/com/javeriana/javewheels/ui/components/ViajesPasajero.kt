package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.entities.WheelPasajero
import com.javeriana.javewheels.ui.theme.JWAmarilloSuave
import com.javeriana.javewheels.ui.theme.JWCelesteContenedor
import com.javeriana.javewheels.ui.theme.JWCelesteSuave

/** Encabezado del flujo de Wheels y reservas. */
@Composable
fun EncabezadoViajes(
    titulo: String,
    onVolver: (() -> Unit)? = null,
    modifier: Modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
    accion: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LogoJaveWheels()
        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
        accion()
        if (onVolver != null) BotonVolver(onClick = onVolver)
    }
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
            .background(if (resaltado) JWAmarilloSuave else JWCelesteContenedor)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

@Composable
fun DatosConductorPasajero(nombre: String, subtitulo: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarUsuario()
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
    subtituloConductor: String = "Conductor · Comunidad Javeriana"
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = JWCelesteSuave)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            DatosConductorPasajero(conductor, subtituloConductor)
            Text(ruta, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Text(horario, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(cupos, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                Text(aporte, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(0.7f))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(detalle, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
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
        Text("${wheel.origen} → ${wheel.destino}", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        Text("${wheel.fecha} · Salida: ${wheel.hora}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        DatosConductorPasajero(wheel.conductor, "${wheel.calificacion} · Comunidad Javeriana")
        Text(wheel.vehiculo, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(JWCelesteSuave).padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(cupos, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            Text("Aporte: ${wheel.aporte}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun PuntoRecogidaPasajero(wheel: WheelPasajero, acordado: Boolean = false) {
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(JWCelesteSuave).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val titulo = if (wheel.modoBuseta) {
            if (acordado) "Modo Buseta · Punto acordado de recogida" else "Modo Buseta · Punto sugerido de recogida"
        } else "Recogida directa · Punto de encuentro"
        Text(titulo, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
        Text(wheel.puntoRecogida, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        Text("${wheel.distancia} · A pie: ${wheel.tiempoAPie}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("${if (acordado) "Hora estimada" else "Paso aproximado"}: ${wheel.horaRecogida}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
