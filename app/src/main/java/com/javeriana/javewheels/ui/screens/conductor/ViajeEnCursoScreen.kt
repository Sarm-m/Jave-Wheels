package com.javeriana.javewheels.ui.screens.conductor

import com.javeriana.javewheels.ui.components.BotonCentradoMapa
import com.javeriana.javewheels.ui.components.EncabezadoMapaFlotante
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
//import com.javeriana.javewheels.ui.components.Pestana

@Composable
fun ViajeEnCursoScreen(
    onVolver: () -> Unit = {},
    onCentrar: () -> Unit = {},
    onAbrirChat: (String) -> Unit = {},
    onSeleccionarPestana: (Pestana) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // CAPA 1: Mapa Ilustrativo reutilizado
        MapaIlustrativo(
            modifier = Modifier.fillMaxSize(),
            altura = null,
            modoEnVivo = true,
            mostrarRutaPredeterminada = false,
            mostrarMarcadorJaveriana = false
        )

        // CAPA 2: Trazo continuo de la ruta
        val colorRuta = Color(0xFF4FA8E0)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centroX = size.width / 2
            val centroY = size.height / 2

            // Inicio: frente del carrito a la izquierda
            val inicioX = centroX - 95.dp.toPx()
            val inicioY = centroY - 20.dp.toPx()

            // Cruce con la avenida principal
            val avenidaX = centroX - 10.dp.toPx()

            // Destino hacia Javeriana
            val destinoY = centroY - 110.dp.toPx()

            val ruta = Path().apply {
                moveTo(inicioX, inicioY)
                lineTo(avenidaX, inicioY) // Avanza por la calle
                lineTo(avenidaX, destinoY) // Dobla y sube a Javeriana
            }

            drawPath(
                path = ruta,
                color = colorRuta,
                style = Stroke(
                    width = 6.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }

        // CAPA 3: Carrito (.png oficial) con badge de pasajeros
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

            // Badge con "2"
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 2.dp, y = (-4).dp)
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2563EB))
                    .border(1.5.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "2", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }

        // CAPA 4: Marcador Javeriana
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 25.dp, y = (-120).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFBBF24)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.School, contentDescription = null, tint = Color(0xFF1E3A8A), modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(text = "Javeriana", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        // CAPA 5: Chip flotante "En curso • Modo Buseta"
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 95.dp, end = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF0D3B66))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF22C55E))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "En curso • Modo Buseta", color = Color.White, fontSize = 11.sp)
            }
        }

        // Botón centrado
        BotonCentradoMapa(onCentrar = onCentrar)

        // Encabezado superior
        EncabezadoMapaFlotante(
            titulo = "Viaje",
            onVolver = onVolver,
            modifier = Modifier.align(Alignment.TopStart)
        )

        // CAPA 8: Panel inferior (BottomSheet) con información y BarraNavegacion
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    // Manija
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(36.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color(0xFFCBD5E1))
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Parada actual
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFEDF6FD)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Place, contentDescription = null, tint = Color(0xFF3B82F6))
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "PARADA ACTUAL", fontSize = 10.sp, color = Color(0xFF3B82F6), fontWeight = FontWeight.Bold)
                            Text(text = "Parkway", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }

                        Text(text = "10:18 a. m.", fontSize = 11.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Pasajeros confirmados
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Pasajeros confirmados", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFEFF6FF))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(text = "2 pasajeros", fontSize = 10.sp, color = Color(0xFF2563EB))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Item 1
                    ItemPasajeroSimple(
                        nombre = "David Santiago G..",
                        detalle = "Parkway • Calle 39",
                        etiqueta = "EN PUNTO",
                        color = Color(0xFF16A34A),
                        fondo = Color(0xFFDCFCE7),
                        onChat = { onAbrirChat("David Santiago Gomez") }
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Item 2
                    ItemPasajeroSimple(
                        nombre = "Fulana Perez",
                        detalle = "Av. 39 • Estación",
                        etiqueta = "A BORDO",
                        color = Color(0xFF2563EB),
                        fondo = Color(0xFFDBEAFE),
                        onChat = { onAbrirChat("Fulana Perez") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Barra de navegación inferior
            BarraNavegacion(
                pestanaActual = Pestana.MisViajes,
                onSeleccionar = onSeleccionarPestana
            )
        }
    }
}

@Composable
private fun ItemPasajeroSimple(
    nombre: String,
    detalle: String,
    etiqueta: String,
    color: Color,
    fondo: Color,
    onChat: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF8FAFC))
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0F2942)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = nombre, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(fondo)
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(text = etiqueta, fontSize = 9.sp, color = color, fontWeight = FontWeight.Bold)
                    }
                }
                Text(text = detalle, fontSize = 10.sp, color = Color.Gray)
            }

            IconButton(onClick = onChat, modifier = Modifier.size(32.dp)) {
                Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViajeEnCursoScreenPreview() {
    ViajeEnCursoScreen()
}