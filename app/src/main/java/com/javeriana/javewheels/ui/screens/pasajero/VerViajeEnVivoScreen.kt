package com.javeriana.javewheels.ui.screens.pasajero

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.MapaIlustrativo
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun VerViajeEnVivoScreen(
    onVolver: () -> Unit = {},
    onCentrar: () -> Unit = {},
    mostrarBarraNavegacion: Boolean = true
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // ====================================================
        // Capa 1: Mapa ilustrativo
        // ====================================================
        MapaIlustrativo(
            modifier = Modifier.fillMaxSize(),
            altura = null,
            modoEnVivo = true
        )

        // ====================================================
        // Capa 2: Carrito sobre la avenida central
        // ====================================================
        Image(
            painter = painterResource(id = R.drawable.carrito),
            contentDescription = "Vehículo en ruta",
            modifier = Modifier
                .size(46.dp)
                .align(Alignment.Center)
        )

        // ====================================================
        // Capa 3: Barra superior (Atrás + Logo JaveWheels)
        // ====================================================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 20.dp, end = 20.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón atrás redondeado celeste
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

            // Logo JaveWheels
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
        // Capa 4: Botón de centrado sobre el parque
        // ====================================================
        IconButton(
            onClick = onCentrar,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp, top = 60.dp)
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
        // Capa 5: Panel inferior + Barra de navegación
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

                    // Tarjeta interna celeste claro
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
                                text = "Tu viaje actual",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
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
                                text = "Llegada en 2 minutos.",
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280)
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Perfil conductor
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
                                        text = "David Santiago",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F3B66)
                                    )
                                    Text(
                                        text = "5 / 5 · Comunidad Javeriana",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // ====================================================
            // Barra de Navegación Inferior (Bottom Bar)
            // ====================================================
            if (mostrarBarraNavegacion) Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemNavInferior(
                    icono = Icons.Default.Home,
                    etiqueta = "Inicio",
                    activo = true
                )
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

@Composable
fun ItemNavInferior(
    icono: ImageVector,
    etiqueta: String,
    activo: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (activo) Color(0xFFE2F0F9) else Color.Transparent)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = etiqueta,
                tint = if (activo) Color(0xFF0F3B66) else Color(0xFF6B7280),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = etiqueta,
            fontSize = 11.sp,
            fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal,
            color = if (activo) Color(0xFF0F3B66) else Color(0xFF6B7280)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerViajeEnVivoScreenPreview() {
    JaveWheelsTheme {
        VerViajeEnVivoScreen()
    }
}
