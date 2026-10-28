package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// Piezas que comparten las pantallas de Acceso.
// ============================================================

/**
 * Encabezado de las pantallas de Acceso:
 * Marca -> (botón Volver) -> acento amarillo -> título -> descripción.
 * Si onVolver es null, no se dibuja el botón Volver (ej. Iniciar sesión).
 */
@Composable
fun EncabezadoAcceso(
    titulo: String,
    descripcion: String,
    modifier: Modifier = Modifier,
    onVolver: (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        MarcaJaveWheels()
        Spacer(modifier = Modifier.height(20.dp))

        if (onVolver != null) {
            BotonVolver(onClick = onVolver)
            Spacer(modifier = Modifier.height(20.dp))
        } else {
            Spacer(modifier = Modifier.height(28.dp))
        }

        AcentoAmarillo()
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = descripcion,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/** Muestra el mensaje de error del ViewModel. Si está vacío no dibuja nada. */
@Composable
fun MensajeError(
    mensaje: String,
    modifier: Modifier = Modifier
) {
    if (mensaje.isNotEmpty()) {
        val solicitud = remember { BringIntoViewRequester() }
        LaunchedEffect(mensaje) { solicitud.bringIntoView() }
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .semantics { liveRegion = LiveRegionMode.Polite }
                .bringIntoViewRequester(solicitud)
        )
    }
}

/** Resuelve el mensaje del estado de UI sin llevar un Context al ViewModel. */
@Composable
fun MensajeError(@androidx.annotation.StringRes mensaje: Int, modifier: Modifier = Modifier) {
    if (mensaje != 0) MensajeError(stringResource(mensaje), modifier)
}

/** Texto de ayuda gris y centrado ("Mínimo 8 caracteres...", etc.). */
@Composable
fun TextoAyuda(
    texto: String,
    modifier: Modifier = Modifier,
    centrado: Boolean = false
) {
    Text(
        text = texto,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = if (centrado) TextAlign.Center else TextAlign.Start,
        modifier = modifier.fillMaxWidth()
    )
}

/** Círculo con el check de "Confirmación exitosa". */
@Composable
fun IconoConfirmacion(modifier: Modifier = Modifier, descripcion: String = stringResource(R.string.ui_confirmacion_exitosa), conDiscoAmarillo: Boolean = false) {
    Box(
        modifier = modifier
            .size(124.dp)
            .clip(CircleShape)
            .background(if (conDiscoAmarillo) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        if (conDiscoAmarillo) {
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = descripcion,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(52.dp)
                )
            }
        } else {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = descripcion,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EncabezadoAccesoPreview() {
    JaveWheelsTheme {
        Column(modifier = Modifier.padding(24.dp)) {
            EncabezadoAcceso(
                titulo = stringResource(R.string.ui_crea_tu_cuenta),
                descripcion = stringResource(R.string.ui_unete_a_la_comunidad_javewheels),
                onVolver = {}
            )
            MensajeError(mensaje = stringResource(R.string.error_completa_todos_los_campos))
            IconoConfirmacion()
        }
    }
}
