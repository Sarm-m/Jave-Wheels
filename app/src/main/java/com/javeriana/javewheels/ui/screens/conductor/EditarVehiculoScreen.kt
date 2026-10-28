package com.javeriana.javewheels.ui.screens.conductor

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.FilaEditable
import com.javeriana.javewheels.ui.components.LogoJaveWheels
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.components.PlacaColombiana
import com.javeriana.javewheels.viewmodels.*
import com.javeriana.javewheels.viewmodels.VehiculoViewModel

@Composable
fun EditarVehiculoScreen(
    viewModel: VehiculoViewModel,
    onGuardado: () -> Unit,
    modifier: Modifier = Modifier
) {
    val estado by viewModel.uiState.collectAsState()
    val focos = remember { List(7) { FocusRequester() } }
    var camposAbiertos by remember { mutableStateOf(emptySet<Int>()) }
    fun actualizarEdicion(indices: Set<Int>, abierto: Boolean) {
        camposAbiertos = if (abierto) camposAbiertos + indices else camposAbiertos - indices
    }
    fun siguiente(indice: Int): FocusRequester? = camposAbiertos.sorted()
        .firstOrNull { it > indice }?.let { focos[it] }


    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        MarcaJaveWheels()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.ui_hola_javeriano),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        LogoJaveWheels(
            tamano = 96.dp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        FilaEditable(
            solicitanteFoco = focos[0], siguienteFoco = siguiente(0),
            alCambiarEdicion = { actualizarEdicion(setOf(0), it) },
            etiqueta = stringResource(R.string.ui_numero_telefonico),
            valor = estado.celular,
            hayError = estado.mensajeError != 0 && (!esCelularValido(estado.celular)),
            mensajeApoyo = if (estado.mensajeError != 0 && (!esCelularValido(estado.celular))) stringResource(R.string.error_escribe_un_celular_valido_de_10_digitos) else null,
            alCambiar = viewModel::actualizarCelular,
            textoEditar = stringResource(R.string.editar),
            textoListo = stringResource(R.string.listo),
            placeholder = stringResource(R.string.ui_escribe_tu_numero_de_celular),
            tipoTeclado = KeyboardType.Phone
        )
        FilaEditable(
            solicitanteFoco = focos[1], siguienteFoco = siguiente(1),
            alCambiarEdicion = { actualizarEdicion(setOf(1), it) },
            etiqueta = stringResource(R.string.ui_placa_del_automovil),
            etiquetaCampo = stringResource(R.string.ui_placa_del_vehiculo),
            valor = estado.placa,
            hayError = estado.mensajeError != 0 && (!esPlacaValida(estado.placa)),
            mensajeApoyo = if (estado.mensajeError != 0 && (!esPlacaValida(estado.placa))) stringResource(R.string.error_la_placa_debe_tener_3_letras_y_3_numeros_ej_abc123) else null,
            alCambiar = viewModel::actualizarPlaca,
            textoEditar = stringResource(R.string.editar),
            textoListo = stringResource(R.string.listo),
            placeholder = stringResource(R.string.ui_escribe_la_placa_del_carro)
        )
        Text(stringResource(R.string.placa_vista_previa), style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(8.dp))
        PlacaColombiana(estado.placa, ancho = 120.dp)
        Spacer(Modifier.height(12.dp))
        FilaEditable(
            solicitanteFoco = focos[2], siguienteFoco = siguiente(2),
            alCambiarEdicion = { actualizarEdicion(setOf(2), it) },
            etiqueta = stringResource(R.string.ui_marca_del_automovil),
            valor = estado.marca,
            hayError = estado.mensajeError != 0 && (estado.marca.isBlank()),
            mensajeApoyo = if (estado.mensajeError != 0 && (estado.marca.isBlank())) stringResource(R.string.campo_obligatorio) else null,
            alCambiar = viewModel::actualizarMarca,
            textoEditar = stringResource(R.string.editar),
            textoListo = stringResource(R.string.listo),
            placeholder = stringResource(R.string.ui_escribe_la_marca)
        )
        FilaEditable(
            solicitanteFoco = focos[3], siguienteFoco = siguiente(3),
            alCambiarEdicion = { actualizarEdicion(setOf(3), it) },
            etiqueta = stringResource(R.string.ui_color_del_vehiculo),
            valor = estado.color,
            hayError = estado.mensajeError != 0 && (estado.color.isBlank()),
            mensajeApoyo = if (estado.mensajeError != 0 && (estado.color.isBlank())) stringResource(R.string.campo_obligatorio) else null,
            alCambiar = viewModel::actualizarColor,
            textoEditar = stringResource(R.string.editar),
            textoListo = stringResource(R.string.listo),
            placeholder = stringResource(R.string.ui_escribe_el_color)
        )
        FilaEditable(
            solicitanteFoco = focos[4], siguienteFoco = siguiente(4),
            alCambiarEdicion = { actualizarEdicion(setOf(4), it) },
            etiqueta = stringResource(R.string.ui_modelo_linea),
            valor = estado.modelo,
            hayError = estado.mensajeError != 0 && (estado.modelo.isBlank()),
            mensajeApoyo = if (estado.mensajeError != 0 && (estado.modelo.isBlank())) stringResource(R.string.campo_obligatorio) else null,
            alCambiar = viewModel::actualizarModelo,
            textoEditar = stringResource(R.string.editar),
            textoListo = stringResource(R.string.listo),
            placeholder = stringResource(R.string.ui_escribe_el_modelo_o_la_linea)
        )
        FilaEditable(
            solicitanteFoco = focos[5], siguienteFoco = siguiente(5),
            alCambiarEdicion = { actualizarEdicion(setOf(5), it) },
            etiqueta = stringResource(R.string.ui_ano_del_vehiculo),
            valor = estado.anio,
            hayError = estado.mensajeError != 0 && (!esAnioValido(estado.anio)),
            mensajeApoyo = if (estado.mensajeError != 0 && (!esAnioValido(estado.anio))) stringResource(R.string.error_escribe_un_ano_valido_del_vehiculo) else null,
            alCambiar = viewModel::actualizarAnio,
            textoEditar = stringResource(R.string.editar),
            textoListo = stringResource(R.string.listo),
            placeholder = stringResource(R.string.ui_ej_2020),
            tipoTeclado = KeyboardType.Number
        )
        FilaEditable(
            solicitanteFoco = focos[6], siguienteFoco = siguiente(6),
            alCambiarEdicion = { actualizarEdicion(setOf(6), it) },
            etiqueta = stringResource(R.string.ui_cupos_del_automovil),
            valor = estado.cupos,
            hayError = estado.mensajeError != 0 && ((estado.cupos.toIntOrNull() ?: 0) < 1),
            mensajeApoyo = if (estado.mensajeError != 0 && ((estado.cupos.toIntOrNull() ?: 0) < 1)) stringResource(R.string.error_debes_ofrecer_al_menos_1_cupo) else null,
            alCambiar = viewModel::actualizarCupos,
            textoEditar = stringResource(R.string.editar),
            textoListo = stringResource(R.string.listo),
            placeholder = stringResource(R.string.ui_ej_3),
            tipoTeclado = KeyboardType.Number
        )

        MensajeError(mensaje = estado.mensajeError)
        Spacer(modifier = Modifier.height(12.dp))
        BotonPrincipal(
            texto = stringResource(R.string.guardar),
            onClick = { if (viewModel.guardarEdicion()) onGuardado() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(160.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}
