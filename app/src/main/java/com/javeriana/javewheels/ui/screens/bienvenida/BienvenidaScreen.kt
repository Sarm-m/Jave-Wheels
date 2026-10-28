package com.javeriana.javewheels.ui.screens.bienvenida

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.LogoJaveWheels
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// "Pantalla Inicio" (bienvenida).
// Se queda en pantalla hasta que el usuario la toque;
// ahí pasa a la pantalla de carga.
// ============================================================

@Composable
fun BienvenidaScreen(
    onTerminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onTerminar() }
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoJaveWheels(tamano = 160.dp)
        Spacer(modifier = Modifier.height(32.dp))
        BoxWithConstraints(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = if (maxWidth / LocalDensity.current.fontScale < 300.dp) 32.sp else 44.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.ui_muevete_facil_y_seguro),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.ui_comunidad_javeriana),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = stringResource(R.string.ui_toca_la_pantalla_para_continuar),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun BienvenidaScreenPreview() {
    JaveWheelsTheme {
        BienvenidaScreen(onTerminar = {})
    }
}
