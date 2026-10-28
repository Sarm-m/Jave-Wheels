package com.javeriana.javewheels.ui.screens.conductor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.components.*
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun ViajeEnCursoScreen(
    onVolver: () -> Unit = {},
    onCentrar: () -> Unit = {},
    onAbrirChat: (String) -> Unit = {},
    onSeleccionarPestana: (Pestana) -> Unit = {},
    mostrarBarraNavegacion: Boolean = true
) {
    Column(Modifier.fillMaxSize()) {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            EncabezadoViajes(stringResource(R.string.ui_viaje), onVolver)
            MapaJaveWheels(altura = 230.dp, alCentrar = { onCentrar() })
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(stringResource(R.string.mapa_viaje_sin_seguimiento), style = MaterialTheme.typography.bodyMedium)
                EtiquetaViaje(stringResource(R.string.ui_en_curso_modo_buseta), resaltado = true)
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(stringResource(R.string.mapa_punto_del_viaje), style = MaterialTheme.typography.labelLarge)
                        Text(stringResource(R.string.ui_parkway), style = MaterialTheme.typography.titleLarge)
                        Text(stringResource(R.string.mapa_horario_demostracion, stringResource(R.string.ui_10_18_a_m)), style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Text(stringResource(R.string.ui_pasajeros_confirmados), style = MaterialTheme.typography.titleMedium)
                Text(stringResource(R.string.ui_2_pasajeros), style = MaterialTheme.typography.bodyMedium)
                PasajeroEnCurso(stringResource(R.string.ui_david_santiago_g),
                    stringResource(R.string.ui_parkway_calle_39), stringResource(R.string.ui_en_punto),
                    onChat = { onAbrirChat("David Santiago Gomez") })
                PasajeroEnCurso(stringResource(R.string.ui_fulana_perez),
                    stringResource(R.string.ui_av_39_estacion), stringResource(R.string.ui_a_bordo),
                    onChat = { onAbrirChat("Fulana Perez") })
            }
        }
        if (mostrarBarraNavegacion) BarraNavegacion(Pestana.MisViajes, onSeleccionarPestana)
    }
}

@Composable
private fun PasajeroEnCurso(nombre: String, detalle: String, estado: String, onChat: () -> Unit) {
    OutlinedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FilaAdaptable(
                principal = { DatosConductorPasajero(nombre, detalle) },
                secundario = {
                    FilledTonalIconButton(onClick = onChat) {
                        Icon(Icons.AutoMirrored.Filled.Chat, stringResource(R.string.ui_enviar_mensaje))
                    }
                }
            )
            EtiquetaViaje(estado)
        }
    }
}

@Preview(showBackground = true, widthDp = 320, fontScale = 1.5f)
@Composable
private fun ViajeEnCursoScreenPreview() {
    JaveWheelsTheme { ViajeEnCursoScreen() }
}
