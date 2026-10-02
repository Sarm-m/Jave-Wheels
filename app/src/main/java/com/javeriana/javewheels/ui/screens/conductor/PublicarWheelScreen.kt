package com.javeriana.javewheels.ui.screens.conductor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun PublicarWheelScreen(
    onBack: () -> Unit = {},
    onPublicar: () -> Unit = {},
    onSeleccionarPestana: (Pestana) -> Unit = {},
    mostrarBarraNavegacion: Boolean = true
) {
    var origen by remember { mutableStateOf("Calle 19A # 71D-30") }
    var destino by remember { mutableStateOf("Pontificia Universidad Javeriana") }
    var fecha by remember { mutableStateOf("2 oct") }
    var hora by remember { mutableStateOf("17:30") }
    var aporte by remember { mutableStateOf("$5.000") }
    var vehiculo by remember { mutableStateOf("Kia Picanto • ABC 123") }
    var modoBusetaActivo by remember { mutableStateOf(true) }

    PantallaBaseLayout(
        titulo = "",
        onBackClick = onBack
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Contenedor principal con scroll vertical
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Encabezado
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LogoJaveWheels(modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Publicar Wheel",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E3A8A)
                    )
                }

                // 1. Tarjeta Recorrido
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Recorrido",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row(modifier = Modifier.fillMaxWidth()) {
                            // Línea vertical que conecta origen y destino
                            Box(
                                modifier = Modifier
                                    .width(18.dp)
                                    .height(80.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(55.dp)
                                        .background(Color.LightGray)
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF3B82F6))
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF59E0B))
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Campos de texto simples
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFF1F5F9))
                                        .padding(8.dp)
                                ) {
                                    Column {
                                        Text(text = "Origen", fontSize = 10.sp, color = Color.Gray)
                                        Text(text = origen, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFF1F5F9))
                                        .padding(8.dp)
                                ) {
                                    Column {
                                        Text(text = "Destino", fontSize = 10.sp, color = Color.Gray)
                                        Text(text = destino, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                    }
                                }
                            }
                        }
                    }
                }

                // 2. Tarjeta Detalles del viaje
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Detalles del viaje",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // Fila de Fecha y Hora
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(text = "Fecha", fontSize = 10.sp, color = Color.Gray)
                                        Text(text = fecha, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Schedule, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(text = "Hora", fontSize = 10.sp, color = Color.Gray)
                                        Text(text = hora, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        // Aporte
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Payments, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = "Aporte por pasajero", fontSize = 10.sp, color = Color.Gray)
                                    Text(text = aporte, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        // Vehículo
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(text = "Vehículo", fontSize = 10.sp, color = Color.Gray)
                                        Text(text = vehiculo, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.Gray)
                            }
                        }
                    }
                }

                // 3. Tarjeta Modo Buseta
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFEFF6FF))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF3B82F6)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.DirectionsBus, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Modo Buseta", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(text = "Recoge pasajeros en puntos intermedios", fontSize = 11.sp, color = Color.Gray)
                        }

                        Switch(
                            checked = modoBusetaActivo,
                            onCheckedChange = { modoBusetaActivo = it },
                            colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFF3B82F6))
                        )
                    }
                }

                // Botón publicar
                Button(
                    onClick = onPublicar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D3B66))
                ) {
                    Text(text = "Publicar Wheel", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Barra inferior estándar
            if (mostrarBarraNavegacion) BarraNavegacion(
                pestanaActual = Pestana.MisViajes,
                onSeleccionar = onSeleccionarPestana
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PublicarWheelScreenPreview() {
    PublicarWheelScreen()
}
