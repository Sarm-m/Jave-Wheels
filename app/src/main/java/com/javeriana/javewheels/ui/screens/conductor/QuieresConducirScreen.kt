package com.javeriana.javewheels.ui.screens.conductor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BotonSecundario
import com.javeriana.javewheels.ui.components.BotonVolver
import com.javeriana.javewheels.ui.components.LogoJaveWheels
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// "Registro - ¿También quieres conducir?".
// Se llega aquí de dos formas:
//   1. Justo después de crear la cuenta (desdeRegistro = true)
//   2. Desde Inicio, al tocar el chip de rol sin tener vehículo
// ============================================================

@Composable
fun QuieresConducirScreen(
    desdeRegistro: Boolean,
    onVolver: () -> Unit,
    onRegistrarVehiculo: () -> Unit,
    onSerPasajero: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MarcaJaveWheels(modifier = Modifier.fillMaxWidth())

        // Si viene de Inicio, puede volver atrás
        if (!desdeRegistro) {
            Spacer(modifier = Modifier.height(16.dp))
            BotonVolver(onClick = onVolver, modifier = Modifier.align(Alignment.Start))
        }

        Spacer(modifier = Modifier.heightIn(min = 48.dp))
        LogoJaveWheels(tamano = 110.dp)
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = if (desdeRegistro) stringResource(R.string.ui_tu_cuenta_esta_creada) else stringResource(R.string.ui_conviertete_en_conductor),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.ui_tambien_quieres_conducir),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextoAyuda(
            texto = stringResource(R.string.ui_registra_tu_vehiculo_para_publicar_wheels_y_compartir_tus_recorri),
            centrado = true
        )
        Spacer(modifier = Modifier.heightIn(min = 48.dp))

        BotonPrincipal(texto = stringResource(R.string.ui_registrar_mi_vehiculo), onClick = onRegistrarVehiculo)
        Spacer(modifier = Modifier.height(16.dp))
        BotonSecundario(texto = stringResource(R.string.ui_por_ahora_ser_pasajero), onClick = onSerPasajero)
        Spacer(modifier = Modifier.height(32.dp))
        TextoAyuda(texto = stringResource(R.string.ui_tambien_puedes_hacerlo_despues_desde_perfil), centrado = true)
    }
}

@Preview(showSystemUi = true)
@Composable
fun QuieresConducirScreenPreview() {
    JaveWheelsTheme {
        QuieresConducirScreen(desdeRegistro = true, onVolver = {}, onRegistrarVehiculo = {}, onSerPasajero = {})
    }
}
