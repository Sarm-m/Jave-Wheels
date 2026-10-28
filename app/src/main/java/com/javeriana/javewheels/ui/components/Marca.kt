package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// Componentes de marca: logo, "JaveWheels" y acento amarillo.
// Se repiten en casi todas las pantallas de Acceso.
// ============================================================

/** Logo dibujado con Compose: círculo amarillo y carro azul. */
@Composable
fun LogoJaveWheels(
    modifier: Modifier = Modifier,
    tamano: Dp = 44.dp
) {
    Box(
        modifier = modifier.size(tamano).clip(CircleShape).background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.DirectionsCar,
            contentDescription = stringResource(R.string.ui_logo_javewheels),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(tamano * 0.55f)
        )
    }
}

/** Bloque "Marca JaveWheels": logo + nombre. */
@Composable
fun MarcaJaveWheels(
    modifier: Modifier = Modifier,
    colorTexto: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LogoJaveWheels()
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = colorTexto
        )
    }
}

/** Rayita amarilla que va encima de los títulos. */
@Composable
fun AcentoAmarillo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(44.dp)
            .height(5.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(MaterialTheme.colorScheme.tertiary)
    )
}

@Preview(showBackground = true)
@Composable
fun MarcaJaveWheelsPreview() {
    JaveWheelsTheme {
        MarcaJaveWheels()
    }
}
