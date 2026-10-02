package com.javeriana.javewheels.ui.screens.conductor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.components.BarraNavegacion
import com.javeriana.javewheels.ui.components.LogoJaveWheels
import com.javeriana.javewheels.ui.components.MapaIlustrativo
import com.javeriana.javewheels.ui.components.BarraNavegacion


@Composable
fun ViajeEnCursoScreen(
    onVolver: () -> Unit = {},
    onCentrar: () -> Unit = {},
    onAbrirChat: (String) -> Unit = {},
    onSeleccionarPestana: (Pestana) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // ====================================================
        // Capa 1: Fondo del mapa ilustrativo del proyecto
        // ====================================================
        MapaIlustrativo(
            modifier = Modifier.fillMaxSize(),
            altura = null,
            modoEnVivo = true,
            mostrarRutaPredeterminada = false,
            mostrarMarcadorJaveriana = false
        )

        // ====================================================
        // Capa 2: Trazo continuo de la ruta (desde el carro hacia Javeriana)
        // ====================================================
        val colorRuta = Color(0xFF4FA8E0)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val centroX = size.width / 2
            val centroY = size.height / 2

            // Coordenadas calculadas en base a las calles del mapa:
            // 1. Frente de la buseta (posicionada a la izquierda del centro)
            val inicioX = centroX - 95.dp.toPx()
            val inicioY = centroY - 20.dp.toPx()

            // 2. Giro en la intersección de la avenida central
            val giroAvenidaX = centroX - 10.dp.toPx()

            // 3. Subida hacia la zona de Javeriana
            val destinoY = centroY - 110.dp.toPx()

            val rutaPath = Path().apply {
                moveTo(inicioX, inicioY)
                lineTo(giroAvenidaX, inicioY)         // Avanza horizontalmente por la calle
                lineTo(giroAvenidaX, destinoY)        // Sube hacia la Javeriana
            }

            drawPath(
                path = rutaPath,
                color = colorRuta,
                style = Stroke(
                    width = 6.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }

        // ====================================================
        // Capa 3: Vehículo (R.drawable.carrito) con badge de pasajeros
        // ====================================================
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-125).dp, y = (-20).dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.carrito),
                contentDescription = "Buseta en ruta",
                modifier = Modifier.size(52.dp)
            )

            // Badge con número de pasajeros ("2")
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 2.dp, y = (-6).dp)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2563EB))
                    .border(1.5.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "2",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // ====================================================
        // Capa 4: Marcador Javeriana
        // ====================================================
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 25.dp, y = (-120).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .shadow(4.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color(0xFFFBBF24)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "Javeriana",
                    tint = Color(0xFF1E3A8A),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Box(
                modifier = Modifier
                    .shadow(2.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Javeriana",
                    color = Color(0xFF0F172A),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // ====================================================
        // Capa 5: Chip flotante "En curso • Modo Buseta"
        // ====================================================
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 95.dp, end = 16.dp)
                .shadow(4.dp, RoundedCornerShape(18.dp))
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFF0D3B66))
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF22C55E))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "En curso • Modo Buseta",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // ====================================================
        // Capa 6: Botón flotante de centrado / GPS
        // ====================================================
        IconButton(
            onClick = onCentrar,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp, bottom = 40.dp)
                .size(46.dp)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "Centrar",
                tint = Color(0xFF0D3B66),
                modifier = Modifier.size(22.dp)
            )
        }

        // ====================================================
        // Capa 7: Barra superior (Botón volver + Logo + Título)
        // ====================================================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 20.dp, end = 20.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onVolver,
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF86C5E8))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFF0F3B66)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))
            LogoJaveWheels(modifier = Modifier.size(42.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Viaje",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3A8A)
            )
        }

        // ====================================================
        // Capa 8: Panel inferior (BottomSheet) con información y BarraNavegacion
        // ====================================================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    // Manija superior gris
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(40.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color(0xFFCBD5E1))
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Parada actual
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFFEDF6FD)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null,
                                tint = Color(0xFF3B82F6),
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "PARADA ACTUAL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3B82F6),
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Parkway",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        }

                        Text(
                            text = "10:18 a. m.",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Encabezado de pasajeros
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Pasajeros confirmados",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFEFF6FF))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "2 pasajeros",
                                color = Color(0xFF2563EB),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Pasajero 1: David Santiago Gomez (EN PUNTO)
                    ItemPasajeroRuta(
                        nombre = "David Santiago G..",
                        detalle = "Parkway • Calle 39",
                        etiquetaEstado = "EN PUNTO",
                        colorEstado = Color(0xFF16A34A),
                        fondoEstado = Color(0xFFDCFCE7),
                        onChatClick = { onAbrirChat("David Santiago Gomez") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Pasajero 2: Fulana Perez (A BORDO)
                    ItemPasajeroRuta(
                        nombre = "Fulana Perez",
                        detalle = "Av. 39 • Estación",
                        etiquetaEstado = "A BORDO",
                        colorEstado = Color(0xFF2563EB),
                        fondoEstado = Color(0xFFDBEAFE),
                        onChatClick = { onAbrirChat("Fulana Perez") }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            // Barra de navegación institucional
            BarraNavegacion(
                pestanaActual = Pestana.MisViajes,
                onSeleccionar = onSeleccionarPestana
            )
        }
    }
}

@Composable
private fun ItemPasajeroRuta(
    nombre: String,
    detalle: String,
    etiquetaEstado: String,
    colorEstado: Color,
    fondoEstado: Color,
    onChatClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFF8FAFC))
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(14.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0F2942)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = nombre,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(fondoEstado)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = etiquetaEstado,
                            color = colorEstado,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = detalle,
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }

            IconButton(
                onClick = onChatClick,
                modifier = Modifier
                    .size(36.dp)
                    .border(1.dp, Color(0xFFCBD5E1), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = "Chat",
                    tint = Color(0xFF3B82F6),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViajeEnCursoScreenPreview() {
    ViajeEnCursoScreen()
}