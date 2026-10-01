package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.theme.JWCelesteContenedor
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// Botones reutilizables de la app.
// Todos reciben un onClick: () -> Unit. El botón no decide qué
// pasa al tocarlo: eso lo decide quien lo usa.
// ============================================================

/** Botón azul relleno (acción principal): "Iniciar sesión", "Crear cuenta"... */
@Composable
fun BotonPrincipal(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icono: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        if (icono != null) {
            Icon(imageVector = icono, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text = texto, style = MaterialTheme.typography.labelLarge)
    }
}

/** Botón con borde (acción secundaria): "Crear una cuenta", "Por ahora, ser pasajero"... */
@Composable
fun BotonSecundario(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
    ) {
        Text(text = texto, style = MaterialTheme.typography.labelLarge)
    }
}

/** Botón de solo texto (enlaces): "¿Olvidaste tu contraseña?", "Volver a iniciar sesión"... */
@Composable
fun BotonTexto(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}

/** Botón cuadrado con flecha para volver atrás ("Volver" en Figma). */
@Composable
fun BotonVolver(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(JWCelesteContenedor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Volver",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BotonesPreview() {
    JaveWheelsTheme {
        Column(modifier = Modifier.padding(24.dp)) {
            BotonVolver(onClick = {})
            Spacer(modifier = Modifier.height(16.dp))
            BotonPrincipal(texto = "Iniciar sesión", onClick = {})
            Spacer(modifier = Modifier.height(16.dp))
            BotonSecundario(texto = "Crear una cuenta", onClick = {})
            BotonTexto(texto = "¿Olvidaste tu contraseña?", onClick = {})
        }
    }
}
