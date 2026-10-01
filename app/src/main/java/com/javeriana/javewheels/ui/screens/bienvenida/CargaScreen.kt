package com.javeriana.javewheels.ui.screens.bienvenida

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.entities.paginasCarga
import com.javeriana.javewheels.ui.components.AcentoAmarillo
import com.javeriana.javewheels.ui.components.BotonVolver
import com.javeriana.javewheels.ui.theme.JWAmarillo
import com.javeriana.javewheels.ui.theme.JWBordeSuave
import com.javeriana.javewheels.ui.theme.JWCelesteContenedor
import com.javeriana.javewheels.ui.theme.JWCelesteSuave
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.CargaViewModel

// ============================================================
// Carga: "Pantalla Carga 1 - Reserva", "2 - Seguimiento" y
// "3 - Roles" de Figma. Es UNA sola pantalla que cambia de contenido
// según la página actual guardada en el CargaViewModel.
// ============================================================

@Composable
fun CargaScreen(
    onComenzar: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CargaViewModel = viewModel()
) {
    // collectAsState(): el StateFlow se vuelve State de Compose.
    // Cada vez que el ViewModel cambia la página, la pantalla se recompone.
    val uiState by viewModel.uiState.collectAsState()
    val pagina = paginasCarga[uiState.paginaActual]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Fila superior: volver (desde la página 2) y "Saltar"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (uiState.paginaActual > 0) {
                BotonVolver(onClick = { viewModel.paginaAnterior() })
            } else {
                Spacer(modifier = Modifier.size(44.dp))
            }
            TextButton(onClick = onComenzar) {
                Text("Saltar", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // weight(1f) en los Spacer = reparten el espacio libre arriba y abajo
        Spacer(modifier = Modifier.weight(1f))
        IlustracionCarga(
            icono = pagina.icono,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(1f))

        // Texto de la pantalla de carga
        AcentoAmarillo()
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = pagina.titulo,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = pagina.descripcion,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Indicador de página + botón Siguiente / Comenzar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IndicadorPagina(total = uiState.totalPaginas, actual = uiState.paginaActual)
            Button(
                onClick = {
                    // Evento -> ViewModel. En la última página se sale de la pantalla de carga.
                    if (uiState.esUltimaPagina) onComenzar() else viewModel.siguientePagina()
                },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.height(48.dp)
            ) {
                Text(
                    text = if (uiState.esUltimaPagina) "Comenzar" else "Siguiente",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

/** Ilustración simple: círculos concéntricos con un ícono grande. */
@Composable
fun IlustracionCarga(
    icono: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(280.dp)
            .clip(CircleShape)
            .background(JWCelesteSuave),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .background(JWCelesteContenedor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(110.dp)
            )
        }
        // Detalle amarillo arriba a la derecha
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp, end = 30.dp)
                .size(44.dp)
                .clip(CircleShape)
                .background(JWAmarillo)
        )
    }
}

/** Puntos del indicador de página: el actual es más largo y azul. */
@Composable
fun IndicadorPagina(
    total: Int,
    actual: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        repeat(total) { indice ->
            val esActual = indice == actual
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(if (esActual) 26.dp else 8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (esActual) MaterialTheme.colorScheme.primary else JWBordeSuave)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CargaScreenPreview() {
    JaveWheelsTheme {
        CargaScreen(onComenzar = {})
    }
}
