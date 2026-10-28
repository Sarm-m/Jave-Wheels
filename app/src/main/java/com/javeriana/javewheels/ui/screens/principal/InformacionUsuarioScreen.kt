package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.AvatarUsuario
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.FilaConBoton
import com.javeriana.javewheels.ui.components.InformacionVehiculo
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.viewmodels.InicioViewModel
import com.javeriana.javewheels.viewmodels.PerfilViewModel

@Composable
fun InformacionUsuarioScreen(
    inicioViewModel: InicioViewModel,
    perfilViewModel: PerfilViewModel,
    onEditarPerfil: () -> Unit,
    onEditarVehiculo: () -> Unit,
    onRegistrarVehiculo: () -> Unit,
    onCerrarSesion: () -> Unit,
    vehiculo: com.javeriana.javewheels.viewmodels.VehiculoUiState,
    modifier: Modifier = Modifier
) {
    val estadoInicio by inicioViewModel.uiState.collectAsState()
    val estadoPerfil by perfilViewModel.uiState.collectAsState()
    val tieneVehiculo = estadoInicio.esConductorRegistrado

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        MarcaJaveWheels()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.ui_hola_javeriano),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AvatarUsuario(
                    foto = estadoPerfil.fotoPerfil,
                    descripcion = stringResource(R.string.ui_foto_de_perfil),
                    modifier = Modifier.size(112.dp).align(Alignment.CenterHorizontally)
                )
                Text(estadoPerfil.nombre, style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally))
                FilaConBoton(stringResource(R.string.ui_datos_de_perfil), stringResource(R.string.editar), onEditarPerfil,
                    accionCompacta = true)
            }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FilaConBoton(
                    etiqueta = stringResource(R.string.ui_datos_de_vehiculo),
                    textoBoton = stringResource(if (tieneVehiculo) R.string.editar else R.string.ui_registrar),
                    onClick = if (tieneVehiculo) onEditarVehiculo else onRegistrarVehiculo,
                    accionCompacta = true
                )
                if (tieneVehiculo) {
                    val marcaModelo = listOf(vehiculo.marca, vehiculo.modelo).map { it.trim() }
                        .filter { it.isNotBlank() }.joinToString(" ")
                    val resumen = listOf(marcaModelo, vehiculo.anio, vehiculo.color, vehiculo.placa)
                        .map { it.trim() }.filter { it.isNotBlank() }.joinToString(" · ")
                    InformacionVehiculo(resumen)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        BotonPrincipal(
            texto = stringResource(R.string.ui_cerrar_sesion),
            onClick = {
                inicioViewModel.cerrarSesion()
                onCerrarSesion()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(220.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}
