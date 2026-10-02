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
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.components.BarraNavegacion
import com.javeriana.javewheels.ui.components.LogoJaveWheels
import com.javeriana.javewheels.ui.components.PantallaBaseLayout
import com.javeriana.javewheels.ui.components.BarraNavegacion

@Composable
fun PublicarWheelScreen(
    onBack: () -> Unit = {},
    onPublicar: () -> Unit = {},
    onSeleccionarPestana: (Pestana) -> Unit = {}
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
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 0. Encabezado: Logo y Título
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LogoJaveWheels(modifier = Modifier.size(44.dp))
                    Text(
                        text = "Publicar Wheel",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E3A8A)
                    )
                }

                // 1. Tarjeta: Recorrido
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(18.dp))
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White)
                        .padding(18.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text(
                            text = "Recorrido",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(110.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(70.dp)
                                        .background(Color(0xFFCBD5E1))
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF4FA8E0))
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFF59E0B))
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                CampoRecorridoItem(etiqueta = "Origen", valor = origen)
                                CampoRecorridoItem(etiqueta = "Destino", valor = destino)
                            }
                        }
                    }
                }

                // 2. Tarjeta: Detalles del viaje
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(18.dp))
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White)
                        .padding(18.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text(
                            text = "Detalles del viaje",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                CampoSelectorConIcono(
                                    etiqueta = "Fecha",
                                    valor = fecha,
                                    icono = Icons.Default.CalendarMonth,
                                    tieneFlecha = true
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                CampoSelectorConIcono(
                                    etiqueta = "Hora",
                                    valor = hora,
                                    icono = Icons.Default.Schedule,
                                    tieneFlecha = true
                                )
                            }
                        }

                        CampoSelectorConIcono(
                            etiqueta = "Aporte por pasajero",
                            valor = aporte,
                            icono = Icons.Default.Payments,
                            tieneFlecha = false
                        )

                        CampoSelectorConIcono(
                            etiqueta = "Vehículo",
                            valor = vehiculo,
                            icono = Icons.Default.DirectionsCar,
                            tieneFlecha = true
                        )
                    }
                }

                // 3. Tarjeta: Modo Buseta
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFFEDF6FD))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF4FA8E0)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DirectionsBus,
                                contentDescription = "Buseta",
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Modo Buseta",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF0F172A)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Recoge pasajeros en puntos intermedios de tu ruta",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B),
                                lineHeight = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Switch(
                            checked = modoBusetaActivo,
                            onCheckedChange = { modoBusetaActivo = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF4FA8E0),
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Color(0xFFCBD5E1)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onPublicar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D3B66))
                ) {
                    Text(
                        text = "Publicar Wheel",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Barra inferior institucional
            BarraNavegacion(
                pestanaActual = Pestana.MisViajes,
                onSeleccionar = onSeleccionarPestana
            )
        }
    }
}

@Composable
private fun CampoRecorridoItem(
    etiqueta: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF1F5F9))
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Column {
            Text(text = etiqueta, fontSize = 11.sp, color = Color(0xFF64748B))
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = valor,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E293B)
            )
        }
    }
}

@Composable
private fun CampoSelectorConIcono(
    etiqueta: String,
    valor: String,
    icono: ImageVector,
    tieneFlecha: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(width = 1.dp, color = Color(0xFFE2E8F0), shape = RoundedCornerShape(14.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFEDF6FD)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = etiqueta,
                    tint = Color(0xFF4FA8E0),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = etiqueta, fontSize = 11.sp, color = Color(0xFF64748B))
                Text(
                    text = valor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }

            if (tieneFlecha) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Desplegar",
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PublicarWheelScreenPreview() {
    PublicarWheelScreen()
}