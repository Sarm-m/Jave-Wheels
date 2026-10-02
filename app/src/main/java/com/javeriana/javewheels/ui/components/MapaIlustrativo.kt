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
import androidx.compose.ui.graphics.Color
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

@Composable
fun MapaIlustrativo(
    modifier: Modifier = Modifier,
    onCentrarUbicacion: () -> Unit = {},
    altura: Dp? = 300.dp,
    modoEnVivo: Boolean = false,
    mostrarRutaPredeterminada: Boolean = true,
    mostrarMarcadorJaveriana: Boolean = true
) {
    val boxModifier = if (altura != null) {
        modifier
            .fillMaxWidth()
            .height(altura)
    } else {
        modifier
    }

    Box(
        modifier = boxModifier
            .background(Color(0xFFF3F5F7)) // Fondo de calles
    ) {
        if (modoEnVivo) {
            // ==================== MODO EN VIVO ====================

            // 1. Avenida 
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(42.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFD6DEE4))
            )

            // 2. Parque verde a la derecha (más ancho)
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxWidth(0.28f)
                    .fillMaxHeight()
                    .background(Color(0xFFDCEAD4))
            )

            // 3. cuadra en cuadrícula alargada que cubre toda la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(6) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // cuadras lado izquierdo (2 columnas)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(90.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE4E9ED))
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(90.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE4E9ED))
                        )

                        // Espacio sobre la avenida 
                        Spacer(modifier = Modifier.width(42.dp))

                        // cuadra lado derecho
                        Box(
                            modifier = Modifier
                                .weight(1.1f)
                                .height(90.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE4E9ED))
                        )

                        // Margen parque
                        Spacer(modifier = Modifier.fillMaxWidth(0.22f))
                    }
                }
            }

            // 4. Línea de ruta celeste desde el carro hacia la Javeriana
            if (mostrarRutaPredeterminada) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 170.dp)
                        .width(6.dp)
                        .height(130.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color(0xFF4FA8E0))
                )
            }

            // 5. Marcador de Javeriana
            if (mostrarMarcadorJaveriana) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 180.dp, start = 85.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF9C846)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "Javeriana",
                            tint = Color(0xFF1E3A8A),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Javeriana",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF1E3A8A),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(horizontal = 14.dp, vertical = 4.dp)
                    )
                }
            }

        } else {
            // ==================== MODO ESTÁNDAR  ====================
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
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
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MapaManzana)
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(80.dp)
                    .fillMaxHeight()
                    .background(MapaParque)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 70.dp, end = 70.dp),
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
                    text = "Javeriana",
                    style = MaterialTheme.typography.labelMedium,
                    color = JWAzul,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(JWBlanco)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 70.dp, bottom = 70.dp),
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
                    text = "Tu ubicación",
                    style = MaterialTheme.typography.labelMedium,
                    color = JWAzul
                )
            }

            Box(
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
}

@Preview(showBackground = true)
@Composable
fun MapaIlustrativoPreview() {
    JaveWheelsTheme {
        MapaIlustrativo()
    }
}
