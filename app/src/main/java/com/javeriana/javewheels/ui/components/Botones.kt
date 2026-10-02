package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    icono: ImageVector? = null,
    compacto: Boolean = false,
    colorFondo: Color = MaterialTheme.colorScheme.primary,
    colorTexto: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        modifier = if (compacto) {
            modifier
                .widthIn(min = 84.dp)
                .height(32.dp)
        } else {
            modifier
                .fillMaxWidth()
                .height(52.dp)
        },
        shape = RoundedCornerShape(if (compacto) 16.dp else 14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo,
            contentColor = colorTexto
        ),
        contentPadding = if (compacto) {
            PaddingValues(horizontal = 20.dp, vertical = 0.dp)
        } else {
            ButtonDefaults.ContentPadding
        }
    ) {
        if (icono != null) {
            Icon(imageVector = icono, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text = texto,
            style = if (compacto) MaterialTheme.typography.labelMedium else MaterialTheme.typography.labelLarge)
    }
}

/** Botón con borde (acción secundaria): "Crear una cuenta", "Por ahora, ser pasajero"... */
@Composable
fun BotonSecundario(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors()
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = colors,
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
