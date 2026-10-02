package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.entities.usuarioDePrueba
import com.javeriana.javewheels.ui.components.AvatarUsuario
import com.javeriana.javewheels.ui.components.AcentoAmarillo
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BotonSecundario
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JWCelesteSuave
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.InicioViewModel

// ============================================================
// Pestaña "Perfil".
// ============================================================

@Composable
fun PerfilScreen(
    onRegistrarVehiculo: () -> Unit,
    onCerrarSesion: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InicioViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        AcentoAmarillo()
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Perfil",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta con los datos del usuario (quemados)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = JWCelesteSuave)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AvatarUsuario(tamano = 56.dp)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = usuarioDePrueba.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = usuarioDePrueba.correo, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "Rol actual: ${uiState.rol.titulo}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Si todavía no es conductor, puede registrar su vehículo desde aquí
        if (uiState.esConductorRegistrado) {
            TextoAyuda(texto = "Tu perfil de conductor está activo. Cambia de rol desde Inicio.")
        } else {
            BotonPrincipal(texto = "Registrar mi vehículo", onClick = onRegistrarVehiculo)
        }
        Spacer(modifier = Modifier.height(16.dp))
        BotonSecundario(
            texto = "Cerrar sesión",
            onClick = {
                viewModel.cerrarSesion()
                onCerrarSesion()
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    JaveWheelsTheme {
        PerfilScreen(onRegistrarVehiculo = {}, onCerrarSesion = {})
    }
}
