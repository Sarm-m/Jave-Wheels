package com.javeriana.javewheels.ui.screens.pasajero

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.MapaIlustrativo
import com.javeriana.javewheels.ui.screens.ItemNavInferior
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun VerViajeBusetaScreen(
    onVolver: () -> Unit = {},
    onCentrar: () -> Unit = {},
    onCompartirViaje: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // ====================================================
        // Capa 1: Fondo de mapa (sin marcador superior)
        // ====================================================
        MapaIlustrativo(
            modifier = Modifier.fillMaxSize(),
            altura = null,
            modoEnVivo = true,
            mostrarRutaPredeterminada = false,
            mostrarMarcadorJaveriana = false
        )

        // ====================================================
// Capa 2: Trazo continuo de la ruta con Canvas
// ====================================================
        val colorRuta = Color(0xFF4FA8E0)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val centroX = size.width / 2
            val centroY = size.height / 2

            // Coordenadas calculadas relativas a las calles del mapa
            // 1. Calle de inicio (donde está el vehículo en x = 5.dp):
            val inicioX = centroX + 5.dp.toPx()
            val inicioY = centroY - 95.dp.toPx() // Delante del carro

            // 2. Intersección horizontal:
            val giroHorizontalY = centroY - 10.dp.toPx()

            // 3. Calle vertical del punto de recogida (a la izquierda):
            val destinoX = centroX - 110.dp.toPx()
            val destinoY = centroY + 10.dp.toPx()

            // Dibujamos el camino continuo
            val rutaPath = androidx.compose.ui.graphics.Path().apply {
                moveTo(inicioX, inicioY)                  // Punto de partida (carro)
                lineTo(inicioX, giroHorizontalY)          // Baja por la avenida
                lineTo(destinoX, giroHorizontalY)         // Dobla 90° a la izquierda por la calle
                lineTo(destinoX, destinoY)                // Baja al punto de recogida
            }

            drawPath(
                path = rutaPath,
                color = colorRuta,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 6.dp.toPx(),
                    cap = androidx.compose.ui.graphics.StrokeCap.Round,
                    join = androidx.compose.ui.graphics.StrokeJoin.Round
                )
            )
        }

        // ====================================================
        // Capa 3: Punto de recogida concéntrico
        // ====================================================
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 28.dp, bottom = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            // Anillo exterior difuminado
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF90C8EB).copy(alpha = 0.5f))
            )
            // Anillo intermedio
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF67B0E0))
            )
            // Núcleo azul oscuro
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0F3B66))
            )
        }

        // ====================================================
        // Capa 4: Vehículo (desplazado a la derecha y arriba)
        // ====================================================
        Image(
            painter = painterResource(id = R.drawable.carrito),
            contentDescription = "Buseta en ruta",
            modifier = Modifier
                .size(46.dp)
                .align(Alignment.Center)
                .offset(x = 5.dp, y = (-120).dp)
        )

        // ====================================================
        // Capa 5: Barra superior
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
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF86C5E8))
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFF0F3B66)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF9C846)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = Color(0xFF0F3B66),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "JaveWheels",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F3B66)
                )
            }
        }

        // ====================================================
        // Capa 6: Botón flotante de centrado
        // ====================================================
        IconButton(
            onClick = onCentrar,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp, top = 20.dp)
                .size(46.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "Centrar",
                tint = Color(0xFF0F3B66)
            )
        }

        // ====================================================
        // Capa 7: Tarjeta inferior con botón "Compartir viaje"
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
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
                            .background(Color(0xFFD0D7DE))
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Tarjeta interna celeste
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F6FA))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Modo Buseta",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F3B66)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Salitre  →  Javeriana",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F3B66)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Te recojerán en 5 minutos!",
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280)
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Perfil conductora
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFD6E9F7)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = Color(0xFF0F3B66)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = "Alexandra Ramos",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F3B66)
                                    )
                                    Text(
                                        text = "4,8 / 5 · Comunidad Javeriana",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Botón Compartir viaje
                    Button(
                        onClick = onCompartirViaje,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F3B66))
                    ) {
                        Text(
                            text = "Compartir viaje",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                }
            }

            // Barra inferior reutilizada
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemNavInferior(icono = Icons.Default.Home, etiqueta = "Inicio", activo = true)
                ItemNavInferior(
                    icono = Icons.Default.CalendarMonth,
                    etiqueta = "Mis viajes",
                    activo = false
                )
                ItemNavInferior(
                    icono = Icons.Default.ChatBubbleOutline,
                    etiqueta = "Mensajes",
                    activo = false
                )
                ItemNavInferior(
                    icono = Icons.Default.PersonOutline,
                    etiqueta = "Perfil",
                    activo = false
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerViajeBusetaScreenPreview() {
    JaveWheelsTheme {
        VerViajeBusetaScreen()
    }
}