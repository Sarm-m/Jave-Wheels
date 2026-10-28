package com.javeriana.javewheels.ui.screens.principal

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.reservaConfirmada
import com.javeriana.javewheels.ui.components.BotonSecundario
import com.javeriana.javewheels.ui.components.BotonTexto
import com.javeriana.javewheels.ui.components.DatosDetallePasajero
import com.javeriana.javewheels.ui.components.EncabezadoViajes
import com.javeriana.javewheels.ui.components.EtiquetaViaje
import com.javeriana.javewheels.ui.components.MapaIlustrativo
import com.javeriana.javewheels.ui.components.PuntoRecogidaPasajero
import com.javeriana.javewheels.ui.components.compartirViaje
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun DetalleReservaScreen(
    onVolver: () -> Unit,
    onEnviarMensaje: () -> Unit,
    modifier: Modifier = Modifier,
    onVerViaje: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val recursos = LocalResources.current
    val wheel = reservaConfirmada.wheel
    fun mostrarAviso() {
        Toast.makeText(context, recursos.getString(R.string.ui_accion_disponible_proximamente), Toast.LENGTH_SHORT).show()
    }

    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        EncabezadoViajes(stringResource(R.string.ui_detalle_de_reserva), onVolver)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EtiquetaViaje(reservaConfirmada.estado, resaltado = true)
            Text(stringResource(R.string.ui_solicitud_aceptada_modo_buseta), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        MapaIlustrativo(altura = 200.dp, textoUbicacion = stringResource(R.string.ui_recogida), mostrarCentrar = false)
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DatosDetallePasajero(wheel, stringResource(R.string.ui_1_cupo_reservado))
            PuntoRecogidaPasajero(wheel, acordado = true)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                BotonSecundario(stringResource(R.string.ui_enviar_mensaje), onClick = onEnviarMensaje)
                BotonSecundario(stringResource(R.string.ui_compartir_viaje), onClick = { compartirViaje(context, wheel) })
            }
            if (onVerViaje != null) BotonSecundario(stringResource(R.string.ui_ver_viaje_en_vivo), onClick = onVerViaje)
            BotonTexto(stringResource(R.string.ui_cancelar_reserva), onClick = { mostrarAviso() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleReservaScreenPreview() {
    JaveWheelsTheme { DetalleReservaScreen(onVolver = {}, onEnviarMensaje = {}) }
}
