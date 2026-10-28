package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.ui.theme.PlacaAmarillo
import com.javeriana.javewheels.ui.theme.PlacaNegro
import com.javeriana.javewheels.viewmodels.esPlacaValida

@Composable
fun PlacaColombiana(placa: String, modifier: Modifier = Modifier, ancho: Dp = 104.dp) {
    if (!esPlacaValida(placa)) {
        Text(stringResource(R.string.placa_incompleta), modifier, color = MaterialTheme.colorScheme.onSurfaceVariant)
        return
    }
    val normalizada = placa.trim().uppercase()
    val texto = "${normalizada.take(3)} ${normalizada.takeLast(3)}"
    val descripcion = stringResource(R.string.placa_accesible, texto)
    val escala = LocalDensity.current.fontScale
    val forma = RoundedCornerShape(10.dp)
    BoxWithConstraints(
        modifier.widthIn(max = ancho).width(ancho).aspectRatio(2.8f)
            .clip(forma).background(PlacaAmarillo).border(1.dp, PlacaNegro, forma)
            .semantics(mergeDescendants = true) { contentDescription = descripcion },
        contentAlignment = Alignment.Center
    ) {
        // Solo la placa escala sus caracteres con el contenedor para conservar su proporción.
        Text(
            texto, color = PlacaNegro, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold,
            fontSize = (maxWidth.value / (5.5f * escala)).sp,
            lineHeight = (maxWidth.value / (5.5f * escala) * 1.2f).sp,
            maxLines = 1, softWrap = false
        )
    }
}

/** Presenta la identificación del vehículo sin duplicar la placa del resumen existente. */
@Composable
fun InformacionVehiculo(resumen: String, modifier: Modifier = Modifier) {
    val partes = resumen.split('·').map { it.trim() }
    val placa = partes.lastOrNull().orEmpty()
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            if (esPlacaValida(placa)) partes.dropLast(1).joinToString(" · ")
            else resumen.ifBlank { stringResource(R.string.vehiculo_no_disponible) },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (esPlacaValida(placa)) PlacaColombiana(placa)
    }
}

@Preview(showBackground = true, widthDp = 320, fontScale = 1.5f)
@Composable
private fun PlacaColombianaPreview() {
    JaveWheelsTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            PlacaColombiana("ABC123")
            PlacaColombiana("XYZ987", Modifier.width(112.dp))
            PlacaColombiana("")
        }
    }
}
