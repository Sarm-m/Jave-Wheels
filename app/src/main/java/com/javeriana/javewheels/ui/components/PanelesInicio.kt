package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.TripOrigin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.entities.Rol
import com.javeriana.javewheels.entities.Viaje
import com.javeriana.javewheels.entities.proximoViajeConductor
import com.javeriana.javewheels.ui.theme.JWAmarillo
import com.javeriana.javewheels.ui.theme.JWAmarilloSuave
import com.javeriana.javewheels.ui.theme.JWBlanco
import com.javeriana.javewheels.ui.theme.JWBordeSuave
import com.javeriana.javewheels.ui.theme.JWCeleste
import com.javeriana.javewheels.ui.theme.JWCelesteContenedor
import com.javeriana.javewheels.ui.theme.JWCelesteSuave
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// Piezas de las pantallas "Pasajero - Inicio" y "Conductor - Inicio".
// ============================================================

/** Encabezado: avatar + saludo + chip del rol (pasajero o cliente). */
@Composable
fun EncabezadoInicio(
    subtitulo: String,
    rol: Rol,
    onCambiarRol: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(JWCelesteContenedor),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = "Avatar", tint = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.width(12.dp))

        // Saludo (weight(1f) = ocupa todo el espacio que sobra)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "¡Hola, javeriano!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = subtitulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Chip del rol
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(JWCelesteContenedor)
                .clickable { onCambiarRol() }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rol.titulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Default.SwapHoriz,
                contentDescription = "Cambiar rol",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

/** Chip seleccionable ("Ahora" / "Programar"). Relleno azul si está seleccionado. */
@Composable
fun ChipOpcion(
    texto: String,
    icono: ImageVector,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorFondo = if (seleccionado) MaterialTheme.colorScheme.primary else JWCelesteSuave
    val colorContenido = if (seleccionado) JWBlanco else MaterialTheme.colorScheme.primary

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(colorFondo)
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = colorContenido, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(texto, style = MaterialTheme.typography.bodyMedium, color = colorContenido)
    }
}

/** Acceso rápido con borde ("Ir a la Javeriana", "Guardados"). */
@Composable
fun AccesoRapido(
    texto: String,
    icono: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, JWBordeSuave, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(texto, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
    }
}

/** Contenedor blanco con esquinas redondeadas arriba y el tirador gris. */
@Composable
fun PanelInferior(
    modifier: Modifier = Modifier,
    contenido: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .background(JWBlanco)
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        // Tirador del panel
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(36.dp)
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(JWBordeSuave)
        )
        Spacer(modifier = Modifier.height(12.dp))
        contenido()
    }
}

/** "Panel - Buscar un Wheel" del pasajero. */
@Composable
fun PanelPasajero(
    destino: String,
    viajeProgramado: Boolean,
    onDestinoChange: (String) -> Unit,
    onSeleccionarMomento: (Boolean) -> Unit,
    onIrALaJaveriana: () -> Unit,
    onGuardados: () -> Unit,
    onBuscar: () -> Unit,
    modifier: Modifier = Modifier
) {
    PanelInferior(modifier = modifier) {
        Text(
            text = "¿A dónde vas?",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Momento del viaje
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ChipOpcion(
                texto = "Ahora",
                icono = Icons.Default.Schedule,
                seleccionado = !viajeProgramado,
                onClick = { onSeleccionarMomento(false) }
            )
            ChipOpcion(
                texto = "Programar",
                icono = Icons.Default.CalendarMonth,
                seleccionado = viajeProgramado,
                onClick = { onSeleccionarMomento(true) }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Origen y destino
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, JWBordeSuave, RoundedCornerShape(16.dp))
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.TripOrigin, contentDescription = null, tint = JWCeleste, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("Mi ubicación", style = MaterialTheme.typography.bodyLarge)
            }
            HorizontalDivider(color = JWBordeSuave)
            OutlinedTextField(
                value = destino,
                onValueChange = onDestinoChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Escribe tu destino") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Place, contentDescription = null, tint = JWAmarillo) },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Destinos rápidos
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AccesoRapido(
                texto = "Ir a la Javeriana",
                icono = Icons.Default.School,
                onClick = onIrALaJaveriana,
                modifier = Modifier.weight(1f)
            )
            AccesoRapido(
                texto = "Guardados",
                icono = Icons.Default.BookmarkBorder,
                onClick = onGuardados,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        BotonPrincipal(texto = "Buscar Wheels", onClick = onBuscar)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

/** "Panel - Viajes del conductor". */
@Composable
fun PanelConductor(
    proximoViaje: Viaje,
    onPublicar: () -> Unit,
    onVerViaje: () -> Unit,
    modifier: Modifier = Modifier
) {
    PanelInferior(modifier = modifier) {
        Text(
            text = "Comparte tu camino",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
        BotonPrincipal(texto = "Publicar un Wheel", onClick = onPublicar, icono = Icons.Default.Add)
        Spacer(modifier = Modifier.height(12.dp))
        TarjetaProximoViaje(viaje = proximoViaje, onVerViaje = onVerViaje)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

/** "Tarjeta - Próximo viaje": ruta, horario, cupos y estado. */
@Composable
fun TarjetaProximoViaje(
    viaje: Viaje,
    onVerViaje: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = JWCelesteSuave)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Título + estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tu próximo viaje",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = viaje.estado,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(11.dp))
                        .background(JWAmarilloSuave)
                        .padding(horizontal = 12.dp, vertical = 3.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${viaje.origen} → ${viaje.destino}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${viaje.fecha} · ${viaje.hora}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    "${viaje.cuposOcupados} de ${viaje.cuposTotales} cupos",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = onVerViaje,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ver viaje")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PanelesInicioPreview() {
    JaveWheelsTheme {
        Column {
            EncabezadoInicio(subtitulo = "Comparte tu ruta", rol = Rol.CONDUCTOR, onCambiarRol = {})
            PanelConductor(proximoViaje = proximoViajeConductor, onPublicar = {}, onVerViaje = {})
        }
    }
}
