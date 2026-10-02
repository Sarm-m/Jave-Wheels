package com.javeriana.javewheels.ui.screens.conductor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.EncabezadoAcceso
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.VehiculoViewModel

// ============================================================
// "Conductor - Registrar vehículo".
// ============================================================

@Composable
fun RegistrarVehiculoScreen(
    onVolver: () -> Unit,
    onGuardado: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VehiculoViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = "Registra tu vehículo",
            descripcion = "Ingresa los datos de tu vehículo para poder publicar Wheels.",
            onVolver = onVolver
        )
        Spacer(modifier = Modifier.height(24.dp))

        CampoTexto(
            etiqueta = "Placa",
            valor = uiState.placa,
            alCambiar = { viewModel.actualizarPlaca(it) },
            placeholder = "Ej. ABC123"
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            etiqueta = "Marca",
            valor = uiState.marca,
            alCambiar = { viewModel.actualizarMarca(it) },
            placeholder = "Ej. Chevrolet"
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            etiqueta = "Modelo",
            valor = uiState.modelo,
            alCambiar = { viewModel.actualizarModelo(it) },
            placeholder = "Ej. Spark"
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            etiqueta = "Año",
            valor = uiState.anio,
            alCambiar = { viewModel.actualizarAnio(it) },
            placeholder = "Ej. 2020",
            tipoTeclado = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            etiqueta = "Color",
            valor = uiState.color,
            alCambiar = { viewModel.actualizarColor(it) },
            placeholder = "Ej. Gris"
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            etiqueta = "Cupos disponibles",
            valor = uiState.cupos,
            alCambiar = { viewModel.actualizarCupos(it) },
            placeholder = "Ej. 4",
            tipoTeclado = KeyboardType.Number
        )

        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(12.dp))

        BotonPrincipal(
            texto = "Guardar vehículo",
            onClick = { if (viewModel.guardar()) onGuardado() }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegistrarVehiculoScreenPreview() {
    JaveWheelsTheme {
        RegistrarVehiculoScreen(onVolver = {}, onGuardado = {})
    }
}
