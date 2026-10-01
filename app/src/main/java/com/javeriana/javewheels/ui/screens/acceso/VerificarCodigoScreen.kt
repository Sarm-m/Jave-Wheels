package com.javeriana.javewheels.ui.screens.acceso

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = "Revisa tu correo",
            descripcion = "Ingresa el código de 6 dígitos que enviamos a ${uiState.correo}.",
            onVolver = onVolver
        )
        Spacer(modifier = Modifier.height(24.dp))

        CampoTexto(
            etiqueta = "Código de verificación",
            valor = uiState.codigo,
            alCambiar = { viewModel.actualizarCodigo(it) },
            placeholder = "000000",
            tipoTeclado = KeyboardType.Number,
            hayError = uiState.mensajeError.isNotEmpty()
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Dato quemado: como no se envía un correo real, se muestra el código de prueba
        TextoAyuda(texto = "Código de prueba: $CODIGO_VERIFICACION")
        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(16.dp))

        BotonPrincipal(
            texto = "Verificar código",
            onClick = { if (viewModel.verificarCodigo()) onCodigoVerificado() }
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextoAyuda(texto = "¿No llegó? Revisa tu correo no deseado.", centrado = true)
        Spacer(modifier = Modifier.height(8.dp))
        BotonTexto(texto = "Usar otro correo", onClick = onVolver)
    }
}

@Preview(showSystemUi = true)
@Composable
fun VerificarCodigoScreenPreview() {
    JaveWheelsTheme {
        VerificarCodigoScreen(onVolver = {}, onCodigoVerificado = {})
    }
}
