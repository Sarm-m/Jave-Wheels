package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.theme.JWAmarillo
import com.javeriana.javewheels.ui.theme.JWAzul
import com.javeriana.javewheels.ui.theme.JWBlanco
import com.javeriana.javewheels.ui.theme.JWCeleste
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.ui.theme.MapaFondo
import com.javeriana.javewheels.ui.theme.MapaManzana
import com.javeriana.javewheels.ui.theme.MapaParque

// ============================================================
// "Mapa ilustrativo"
// ============================================================

@Composable
fun MapaIlustrativo(
    modifier: Modifier = Modifier,
    onCentrarUbicacion: () -> Unit = {},
    altura: Dp = 300.dp,
    textoUbicacion: String = "Tu ubicación",
    mostrarCentrar: Boolean = true,
    textoDestino: String = "Javeriana"
) {
    val escala = altura.value / 300f
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(altura)
            .background(MapaFondo)
    ) {
        // 1. Manzanas: una cuadrícula de rectángulos grises
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp * escala)
        ) {
            repeat(5) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    repeat(5) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp * escala)
                                .clip(RoundedCornerShape(6.dp))
                                .background(MapaManzana)
                        )
                    }
                }
            }
        }

        // 2. Parque verde a la derecha
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(80.dp)
                .fillMaxHeight()
                .background(MapaParque)
        )

        // 3. Marcador de la Javeriana
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 70.dp * escala, end = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(JWAmarillo),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.School, contentDescription = null, tint = JWAzul)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = textoDestino,
                style = MaterialTheme.typography.labelMedium,
                color = JWAzul,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(JWBlanco)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        // 4. Punto de "Tu ubicación"
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 70.dp, bottom = 70.dp * escala),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(JWAzul)
                    .border(4.dp, JWCeleste, CircleShape)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = textoUbicacion,
                style = MaterialTheme.typography.labelMedium,
                color = JWAzul
            )
        }

        // 5. Botón opcional de centrar ubicación
        if (mostrarCentrar) Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 48.dp)
                .size(44.dp)
                .clip(CircleShape)
                .background(JWBlanco)
                .clickable { onCentrarUbicacion() },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.MyLocation, contentDescription = "Centrar ubicación", tint = JWAzul)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapaIlustrativoPreview() {
    JaveWheelsTheme {
        MapaIlustrativo()
    }
}
