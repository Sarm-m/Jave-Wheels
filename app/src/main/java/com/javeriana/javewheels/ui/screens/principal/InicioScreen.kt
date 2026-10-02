package com.javeriana.javewheels.ui.screens.principal

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.entities.Rol
import com.javeriana.javewheels.ui.components.EncabezadoInicio
import com.javeriana.javewheels.ui.components.MapaIlustrativo
import com.javeriana.javewheels.ui.components.PanelConductor
import com.javeriana.javewheels.ui.components.PanelPasajero
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.InicioViewModel

// ============================================================
// "Pasajero - Inicio" y "Conductor"
// Es UNA pantalla: según el rol del InicioViewModel se muestra
// el panel del pasajero o el del conductor.
// ============================================================

/** Mensaje para las funciones que llegan en la próxima entrega. */
private const val PROXIMAMENTE = "Disponible en la próxima entrega"

@Composable
fun InicioScreen(
    onQuiereSerConductor: () -> Unit,
    onBuscarWheels: () -> Unit = {},
    onGuardados: () -> Unit = {},
    onSeleccionarMomento: (Boolean) -> Unit = {},
    onVerViajeConductor: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: InicioViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Muestra avisos breves para las acciones visuales.
    fun mostrarMensaje(texto: String) {
        Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoInicio(
            subtitulo = if (uiState.rol == Rol.PASAJERO) "Vamos en Wheels" else "Comparte tu ruta",
            rol = uiState.rol,
            onCambiarRol = {
                // Si no tiene vehículo, el ViewModel devuelve false -> lo mandamos a registrarlo
                if (!viewModel.cambiarRol()) onQuiereSerConductor()
            }
        )

        // Box: el panel se dibuja ENCIMA del mapa (se superponen)
        Box {
            MapaIlustrativo(onCentrarUbicacion = { mostrarMensaje("Ubicación centrada") })

            // padding(top) = el panel empieza un poco antes de que termine el mapa
            val modificadorPanel = Modifier.padding(top = 270.dp)

            if (uiState.rol == Rol.PASAJERO) {
                PanelPasajero(
                    origen = uiState.origen,
                    destino = uiState.destino,
                    viajeProgramado = uiState.viajeProgramado,
                    resumenProgramado = "${com.javeriana.javewheels.entities.fechaBusqueda(uiState.salidaProgramadaMillis)} · ${com.javeriana.javewheels.entities.horaBusqueda(uiState.salidaProgramadaMillis)}",
                    onDestinoChange = { viewModel.actualizarDestino(it) },
                    onSeleccionarMomento = onSeleccionarMomento,
                    onIrALaJaveriana = { viewModel.irALaJaveriana() },
                    onGuardados = onGuardados,
                    onBuscar = onBuscarWheels,
                    modifier = modificadorPanel
                )
            } else {
                PanelConductor(
                    proximoViaje = uiState.proximoViaje,
                    onPublicar = { mostrarMensaje(PROXIMAMENTE) },
                    onVerViaje = onVerViajeConductor,
                    modifier = modificadorPanel
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun InicioScreenPreview() {
    JaveWheelsTheme {
        InicioScreen(onQuiereSerConductor = {})
    }
}
