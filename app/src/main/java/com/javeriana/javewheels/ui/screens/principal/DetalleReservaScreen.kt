package com.javeriana.javewheels.ui.screens.principal

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.reservaConfirmada
import com.javeriana.javewheels.ui.components.BotonSecundario
import com.javeriana.javewheels.ui.components.BotonTexto
import com.javeriana.javewheels.ui.components.DatosDetallePasajero
import com.javeriana.javewheels.ui.components.EncabezadoViajes
import com.javeriana.javewheels.ui.components.EtiquetaViaje
import com.javeriana.javewheels.ui.components.PuntoRecogidaPasajero
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun DetalleReservaScreen(onVolver: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val wheel = reservaConfirmada.wheel
    fun mostrarAviso() {
        Toast.makeText(context, "Acción disponible próximamente", Toast.LENGTH_SHORT).show()
    }

    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        EncabezadoViajes("Detalle de reserva", onVolver)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EtiquetaViaje(reservaConfirmada.estado, resaltado = true)
            Text("Solicitud aceptada · Modo Buseta", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
        }
        Image(
            painter = painterResource(R.drawable.mapa_recorrido_reserva),
            contentDescription = "Mapa ilustrativo de la recogida acordada y la Javeriana",
            modifier = Modifier.fillMaxWidth().aspectRatio(390f / 200f)
        )
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DatosDetallePasajero(wheel, "1 cupo reservado")
            PuntoRecogidaPasajero(wheel, acordado = true)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BotonSecundario("Enviar mensaje", onClick = { mostrarAviso() }, modifier = Modifier.weight(1f))
                BotonSecundario("Compartir viaje", onClick = { mostrarAviso() }, modifier = Modifier.weight(1f))
            }
            BotonTexto("Cancelar reserva", onClick = { mostrarAviso() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleReservaScreenPreview() {
    JaveWheelsTheme { DetalleReservaScreen(onVolver = {}) }
}
