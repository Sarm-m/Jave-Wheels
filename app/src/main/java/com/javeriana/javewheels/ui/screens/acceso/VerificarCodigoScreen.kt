package com.javeriana.javewheels.ui.screens.acceso

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.CODIGO_VERIFICACION
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BotonTexto
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.EncabezadoAcceso
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.RecuperarContrasenaViewModel

// ============================================================
// "Acceso - Verificar código"
// ============================================================

@Composable
fun VerificarCodigoScreen(
    onVolver: () -> Unit,
    onCodigoVerificado: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecuperarContrasenaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    fun verificarCodigo() { if (viewModel.verificarCodigo()) onCodigoVerificado() }

    Column(
        modifier = modifier
            .fillMaxSize().imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = stringResource(R.string.ui_revisa_tu_correo),
            descripcion = stringResource(R.string.ui_ingresa_el_codigo_de_6_digitos_que_enviamos_a_1_s, uiState.correo),
            onVolver = onVolver
        )
        Spacer(modifier = Modifier.height(24.dp))

        CampoTexto(
            alCompletar = ::verificarCodigo,
            etiqueta = stringResource(R.string.ui_codigo_de_verificacion),
            valor = uiState.codigo,
            alCambiar = { viewModel.actualizarCodigo(it) },
            placeholder = "000000",
            tipoTeclado = KeyboardType.Number,
            hayError = uiState.mensajeError != 0
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Dato quemado: como no se envía un correo real, se muestra el código de prueba
        TextoAyuda(texto = stringResource(R.string.ui_codigo_de_prueba_1_s, CODIGO_VERIFICACION))
        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(16.dp))

        BotonPrincipal(
            texto = stringResource(R.string.ui_verificar_codigo),
            onClick = ::verificarCodigo
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextoAyuda(texto = stringResource(R.string.ui_no_llego_revisa_tu_correo_no_deseado), centrado = true)
        Spacer(modifier = Modifier.height(8.dp))
        BotonTexto(texto = stringResource(R.string.ui_usar_otro_correo), onClick = onVolver)
    }
}

@Preview(showSystemUi = true)
@Composable
fun VerificarCodigoScreenPreview() {
    JaveWheelsTheme {
        VerificarCodigoScreen(onVolver = {}, onCodigoVerificado = {})
    }
}
