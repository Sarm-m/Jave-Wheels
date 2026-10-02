package com.javeriana.javewheels.ui.screens.conductor

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javeriana.javewheels.ui.components.BarraNavegacion
import com.javeriana.javewheels.ui.components.LogoJaveWheels
import com.javeriana.javewheels.ui.components.PantallaBaseLayout
//import com.javeriana.javewheels.ui.components.Pestana
import com.javeriana.javewheels.navigation.Pestana

@Composable
fun AdministrarWheelScreen(
    onBack: () -> Unit = {},
    onIniciarViaje: () -> Unit = {},
    onCancelarWheel: () -> Unit = {},
    onSeleccionarPestana: (Pestana) -> Unit = {},
    mostrarBarraNavegacion: Boolean = true
) {
    PantallaBaseLayout(
        titulo = "",
        onBackClick = onBack
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Scroll vertical para que quepan las tarjetas en pantallas pequeñas
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header: Logo y Título
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LogoJaveWheels(modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Administrar Wheel",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E3A8A)
                    )
                }

                // 1. Tarjeta Resumen del viaje
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Resumen del viaje",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFFEF3C7))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "• Con solicitudes",
                                    fontSize = 11.sp,
                                    color = Color(0xFFB45309)
                                )
                            }
                        }

                        // Ruta
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row {
                                Text(text = "Origen: ", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                Text(text = "Calle 19A # 71D-30", fontSize = 12.sp)
                            }
                            Row {
                                Text(text = "Destino: ", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                Text(text = "Edificio Ciencia Básicas Javeriana", fontSize = 12.sp)
                            }
                        }

                        // Fecha, hora y modo buseta
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Vie, 2 oct", fontSize = 11.sp, color = Color.Gray)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "7:30 p. m.", fontSize = 11.sp, color = Color.Gray)
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFEFF6FF))
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.DirectionsBus, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color(0xFF1E3A8A))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Modo Buseta", fontSize = 10.sp, color = Color(0xFF1E3A8A))
                                }
                            }
                        }
                    }
                }

                // 2. Tarjeta Solicitudes pendientes
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Solicitudes pendientes",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFDBEAFE)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "1", fontSize = 11.sp, color = Color(0xFF1E3A8A), fontWeight = FontWeight.Bold)
                            }
                        }

                        Text(
                            text = "Prioriza recogidas cercanas a tu recorrido.",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )

                        // Info del pasajero
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE2E8F0)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(text = "David Santiago Gomez", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Place, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                                    Text(text = "Parkway Calle 39 con Carrera 21", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                        }

                        // Botones de acción
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFF1F5F9))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(text = "En tu ruta", fontSize = 11.sp, color = Color(0xFF1E3A8A))
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "Rechazar",
                                color = Color.Red,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { }
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Button(
                                onClick = { },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5CAEE2)),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text(text = "Aceptar", fontSize = 12.sp)
                            }
                        }
                    }
                }

                // 3. Tarjeta Pasajeros confirmados y cupos
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Pasajeros confirmados", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(text = "1 de 3 cupos", color = Color(0xFF2563EB), fontSize = 11.sp)
                        }

                        // 3 barritas de cupos simples con Box
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color(0xFF1E3A8A))
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color.LightGray)
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color.LightGray)
                            )
                        }

                        // Pasajero confirmado
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE2E8F0)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = "Fulana Perez", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    Text(text = "Recogida: Javeriana", fontSize = 10.sp, color = Color.Gray)
                                }
                            }
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(18.dp))
                        }
                    }
                }

                // Botones inferiores de la pantalla
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onCancelarWheel,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                    ) {
                        Text(text = "Cancelar Wheel", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onIniciarViaje,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D3B66))
                    ) {
                        Text(text = "Iniciar Viaje", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Barra inferior
            if (mostrarBarraNavegacion) BarraNavegacion(
                pestanaActual = Pestana.MisViajes,
            onSeleccionar = onSeleccionarPestana
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdministrarWheelScreenPreview() {
    AdministrarWheelScreen()
}
