package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.theme.JWAmarillo
import com.javeriana.javewheels.ui.theme.JWAzul
import com.javeriana.javewheels.ui.theme.JWCeleste
import com.javeriana.javewheels.ui.theme.JWCelesteContenedor
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
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
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
fun IconoConfirmacion(modifier: Modifier = Modifier, descripcion: String = "Confirmación exitosa", conDiscoAmarillo: Boolean = false) {
    Box(
        modifier = modifier
            .size(124.dp)
            .clip(CircleShape)
            .background(if (conDiscoAmarillo) JWCeleste else JWCelesteContenedor),
        contentAlignment = Alignment.Center
    ) {
        if (conDiscoAmarillo) {
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(JWAmarillo),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = descripcion,
                    tint = JWAzul,
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
                titulo = "Crea tu cuenta",
                descripcion = "Únete a la comunidad JaveWheels.",
                onVolver = {}
            )
            MensajeError(mensaje = "Completa todos los campos.")
            IconoConfirmacion()
        }
    }
}
