package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.RutaGuardada
import com.javeriana.javewheels.entities.rutasGuardadas
import com.javeriana.javewheels.ui.components.*

@Composable
fun RutasGuardadasScreen(onVolver: () -> Unit, onElegirRuta: (RutaGuardada) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        EncabezadoViajes(stringResource(R.string.ui_rutas_guardadas), onVolver)
        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(stringResource(R.string.ui_elige_una_ruta_para_preparar_tu_busqueda_de_wheels))
            rutasGuardadas.forEach { ruta ->
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(ruta.nombre, style = MaterialTheme.typography.titleMedium)
                        Text(stringResource(R.string.ruta_busqueda, ruta.origen, ruta.destino))
                        BotonSecundario(stringResource(R.string.ui_usar_ruta), onClick = { onElegirRuta(ruta) })
                    }
                }
            }
        }
    }
}
