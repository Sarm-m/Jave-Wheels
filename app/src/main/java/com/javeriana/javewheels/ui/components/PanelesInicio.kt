package com.javeriana.javewheels.ui.components

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TripOrigin
import androidx.compose.material3.Card
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.Rol
import com.javeriana.javewheels.entities.Viaje
import com.javeriana.javewheels.entities.proximoViajeConductor
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
    FilaAdaptable(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 12.dp),
        principal = {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AvatarUsuario(descripcion = stringResource(R.string.ui_avatar))
                Column(Modifier.weight(1f)) {
                    Text(stringResource(R.string.ui_hola_javeriano),
                        style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                    Text(subtitulo, style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        },
        secundario = { SelectorRol(rol, onCambiarRol) }
    )
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
    FilterChip(
        selected = seleccionado,
        onClick = onClick,
        label = { Text(texto) },
        leadingIcon = { Icon(icono, null, Modifier.size(18.dp)) },
        modifier = modifier,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

/** Acceso rápido con borde ("Ir a la Javeriana", "Guardados"). */
@Composable
fun AccesoRapido(
    texto: String,
    icono: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(onClick = onClick, modifier = modifier.heightIn(min = 48.dp),
        shape = RoundedCornerShape(12.dp)) {
        Icon(icono, null, Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Text(texto, style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f, fill = false))
    }
}

/** Cabecera completa visible también cuando la hoja está recogida. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CabeceraPanelInicio(rol: Rol, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        BottomSheetDefaults.DragHandle()
        Text(stringResource(if (rol == Rol.PASAJERO) R.string.ui_a_donde_vas else R.string.ui_comparte_tu_camino),
            Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
    }
}

/** El cuerpo comparte espaciado; la hoja de Material 3 administra fondo, altura y gestos. */
@Composable
fun PanelInferior(modifier: Modifier = Modifier, contenido: @Composable () -> Unit) {
    Column(modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp)) { contenido() }
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
    modifier: Modifier = Modifier,
    origen: String = "Mi ubicación",
    resumenProgramado: String = "",
    mostrarEncabezado: Boolean = true
) {
    PanelInferior(modifier) {
        if (mostrarEncabezado) {
            Text(stringResource(R.string.ui_a_donde_vas), style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(12.dp))
        }

        // Momento del viaje
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ChipOpcion(
                texto = stringResource(R.string.ui_ahora),
                icono = Icons.Default.Schedule,
                seleccionado = !viajeProgramado,
                onClick = { onSeleccionarMomento(false) }
            )
            ChipOpcion(
                texto = stringResource(R.string.ui_programar),
                icono = Icons.Default.CalendarMonth,
                seleccionado = viajeProgramado,
                onClick = { onSeleccionarMomento(true) }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        if (viajeProgramado && resumenProgramado.isNotBlank()) {
            Text(resumenProgramado, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Origen y destino
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp))
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Icon(Icons.Default.TripOrigin, contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(origen, style = MaterialTheme.typography.bodyLarge)
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            CampoTexto(
                etiqueta = stringResource(R.string.ui_destino),
                accionTeclado = ImeAction.Search,
                alCompletar = onBuscar,
                valor = destino,
                alCambiar = onDestinoChange,
                placeholder = stringResource(R.string.ui_escribe_tu_destino),
                iconoFinal = Icons.Default.Place
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Destinos rápidos
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AccesoRapido(
                texto = stringResource(R.string.ui_ir_a_la_javeriana),
                icono = Icons.Default.School,
                onClick = onIrALaJaveriana,
                modifier = Modifier.fillMaxWidth()
            )
            AccesoRapido(
                texto = stringResource(R.string.ui_guardados),
                icono = Icons.Default.BookmarkBorder,
                onClick = onGuardados,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        BotonPrincipal(texto = stringResource(R.string.ui_buscar_wheels), onClick = onBuscar)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

/** "Panel - Viajes del conductor". */
@Composable
fun PanelConductor(
    proximoViaje: Viaje?,
    onPublicar: () -> Unit,
    onVerViaje: () -> Unit,
    modifier: Modifier = Modifier,
    mostrarEncabezado: Boolean = true
) {
    PanelInferior(modifier) {
        if (mostrarEncabezado) {
            Text(stringResource(R.string.ui_comparte_tu_camino), style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(12.dp))
        }
        BotonPrincipal(texto = stringResource(R.string.ui_publicar_un_wheel), onClick = onPublicar, icono = Icons.Default.Add)
        Spacer(modifier = Modifier.height(12.dp))
        if (proximoViaje != null) {
            TarjetaProximoViaje(viaje = proximoViaje, onVerViaje = onVerViaje)
        } else {
            Text(stringResource(R.string.ui_aun_no_tienes_viajes_programados), color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Título + estado
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.ui_tu_proximo_viaje),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = viaje.estado,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(11.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(horizontal = 12.dp, vertical = 3.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.ruta_busqueda, viaje.origen, viaje.destino),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(stringResource(R.string.fecha_y_hora, viaje.fecha, viaje.hora), style = MaterialTheme.typography.bodyMedium)
                Text(
                    stringResource(R.string.cupos_ocupados_total, viaje.cuposOcupados, viaje.cuposTotales),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = onVerViaje,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(R.string.ui_ver_viaje))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PanelesInicioPreview() {
    JaveWheelsTheme {
        Column {
            EncabezadoInicio(subtitulo = stringResource(R.string.ui_comparte_tu_ruta), rol = Rol.CONDUCTOR, onCambiarRol = {})
            PanelConductor(proximoViaje = proximoViajeConductor, onPublicar = {}, onVerViaje = {})
        }
    }
}
