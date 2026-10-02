package com.javeriana.javewheels.ui.screens.bienvenida

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javeriana.javewheels.ui.components.LogoJaveWheels
import com.javeriana.javewheels.ui.theme.JWAmarillo
import com.javeriana.javewheels.ui.theme.JWAzul
import com.javeriana.javewheels.ui.theme.JWBlanco
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
            .background(JWAzul)
            .clickable { onTerminar() }
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoJaveWheels(tamano = 160.dp)
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "JaveWheels",
            fontSize = 44.sp,
            fontWeight = FontWeight.Medium,
            color = JWBlanco
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "MUÉVETE FÁCIL Y SEGURO",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp,
            color = JWAmarillo,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Comunidad Javeriana",
            fontSize = 16.sp,
            color = JWBlanco.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "Toca la pantalla para continuar",
            fontSize = 14.sp,
            color = JWBlanco.copy(alpha = 0.6f)
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
