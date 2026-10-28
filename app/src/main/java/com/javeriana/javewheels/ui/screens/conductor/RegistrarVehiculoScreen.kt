package com.javeriana.javewheels.ui.screens.conductor

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.EncabezadoAcceso
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.components.PlacaColombiana
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.*
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
    fun guardarVehiculo() { if (viewModel.guardar()) onGuardado() }
    val focos = remember { List(6) { FocusRequester() } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = stringResource(R.string.ui_registra_tu_vehiculo),
            descripcion = stringResource(R.string.ui_ingresa_los_datos_de_tu_vehiculo_para_poder_publicar_wheels),
            onVolver = onVolver
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextoAyuda(stringResource(R.string.campos_obligatorios))

        CampoTexto(
            solicitanteFoco = focos[0], siguienteFoco = focos[1],
            etiqueta = stringResource(R.string.ui_placa),
            valor = uiState.placa,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && (!esPlacaValida(uiState.placa)),
            mensajeApoyo = if (uiState.mensajeError != 0 && (!esPlacaValida(uiState.placa))) stringResource(R.string.error_la_placa_debe_tener_3_letras_y_3_numeros_ej_abc123) else null,
            alCambiar = { viewModel.actualizarPlaca(it) },
            placeholder = stringResource(R.string.ui_ej_abc123)
        )
        Text(stringResource(R.string.placa_vista_previa), style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(8.dp))
        PlacaColombiana(uiState.placa, ancho = 120.dp)
        Spacer(Modifier.height(12.dp))
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            solicitanteFoco = focos[1], siguienteFoco = focos[2],
            etiqueta = stringResource(R.string.ui_marca),
            valor = uiState.marca,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && (uiState.marca.isBlank()),
            mensajeApoyo = if (uiState.mensajeError != 0 && (uiState.marca.isBlank())) stringResource(R.string.campo_obligatorio) else null,
            alCambiar = { viewModel.actualizarMarca(it) },
            placeholder = stringResource(R.string.ui_ej_chevrolet)
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            solicitanteFoco = focos[2], siguienteFoco = focos[3],
            etiqueta = stringResource(R.string.ui_modelo),
            valor = uiState.modelo,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && (uiState.modelo.isBlank()),
            mensajeApoyo = if (uiState.mensajeError != 0 && (uiState.modelo.isBlank())) stringResource(R.string.campo_obligatorio) else null,
            alCambiar = { viewModel.actualizarModelo(it) },
            placeholder = stringResource(R.string.ui_ej_spark)
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            solicitanteFoco = focos[3], siguienteFoco = focos[4],
            etiqueta = stringResource(R.string.ui_ano),
            valor = uiState.anio,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && (!esAnioValido(uiState.anio)),
            mensajeApoyo = if (uiState.mensajeError != 0 && (!esAnioValido(uiState.anio))) stringResource(R.string.error_escribe_un_ano_valido_del_vehiculo) else null,
            alCambiar = { viewModel.actualizarAnio(it) },
            placeholder = stringResource(R.string.ui_ej_2020),
            tipoTeclado = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            solicitanteFoco = focos[4], siguienteFoco = focos[5],
            etiqueta = stringResource(R.string.ui_color),
            valor = uiState.color,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && (uiState.color.isBlank()),
            mensajeApoyo = if (uiState.mensajeError != 0 && (uiState.color.isBlank())) stringResource(R.string.campo_obligatorio) else null,
            alCambiar = { viewModel.actualizarColor(it) },
            placeholder = stringResource(R.string.ui_ej_gris)
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            solicitanteFoco = focos[5], alCompletar = ::guardarVehiculo,
            etiqueta = stringResource(R.string.ui_cupos_disponibles),
            valor = uiState.cupos,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && ((uiState.cupos.toIntOrNull() ?: 0) < 1),
            mensajeApoyo = if (uiState.mensajeError != 0 && ((uiState.cupos.toIntOrNull() ?: 0) < 1)) stringResource(R.string.error_debes_ofrecer_al_menos_1_cupo) else null,
            alCambiar = { viewModel.actualizarCupos(it) },
            placeholder = stringResource(R.string.ui_ej_4),
            tipoTeclado = KeyboardType.Number
        )

        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(12.dp))

        BotonPrincipal(
            texto = stringResource(R.string.ui_guardar_vehiculo),
            onClick = ::guardarVehiculo
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
