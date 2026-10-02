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
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.entities.RutaGuardada
import com.javeriana.javewheels.entities.rutasGuardadas
import com.javeriana.javewheels.ui.components.*
import com.javeriana.javewheels.ui.theme.JWCelesteSuave

@Composable
fun RutasGuardadasScreen(onVolver: () -> Unit, onElegirRuta: (RutaGuardada) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        EncabezadoViajes("Rutas guardadas", onVolver)
        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Elige una ruta para preparar tu búsqueda de Wheels.")
            rutasGuardadas.forEach { ruta ->
                Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = JWCelesteSuave)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(ruta.nombre, style = MaterialTheme.typography.titleMedium)
                        Text("${ruta.origen} → ${ruta.destino}")
                        BotonSecundario("Usar ruta", onClick = { onElegirRuta(ruta) })
                    }
                }
            }
        }
    }
}
