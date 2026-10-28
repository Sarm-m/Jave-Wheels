package com.javeriana.javewheels.ui.screens.acceso

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BotonTexto
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.EncabezadoAcceso
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.RecuperarContrasenaViewModel

// ============================================================
// "Acceso - Recuperar contraseña".
// ============================================================

@Composable
fun RecuperarContrasenaScreen(
    onVolver: () -> Unit,
    onCodigoEnviado: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecuperarContrasenaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    fun enviarCodigo() { if (viewModel.enviarCodigo()) onCodigoEnviado() }

    Column(
        modifier = modifier
            .fillMaxSize().imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = stringResource(R.string.ui_olvidaste_tu_contrasena_2),
            descripcion = stringResource(R.string.ui_ingresa_el_correo_asociado_a_tu_cuenta_te_enviaremos_un_codigo_pa),
            onVolver = onVolver
        )
        Spacer(modifier = Modifier.height(24.dp))

        CampoTexto(
            alCompletar = ::enviarCodigo,
            etiqueta = stringResource(R.string.ui_correo_institucional),
            valor = uiState.correo,
            alCambiar = { viewModel.actualizarCorreo(it) },
            placeholder = stringResource(R.string.ui_nombre_javeriana_edu_co),
            tipoTeclado = KeyboardType.Email,
            hayError = uiState.mensajeError != 0
        )
        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(16.dp))
        BotonPrincipal(
            texto = stringResource(R.string.ui_enviar_codigo),
            onClick = ::enviarCodigo
        )
        Spacer(modifier = Modifier.height(32.dp))

        // "Ayuda con el correo"
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.ui_no_encuentras_el_mensaje),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.ui_revisa_tambien_la_carpeta_de_correo_no_deseado),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        BotonTexto(texto = stringResource(R.string.ui_volver_a_iniciar_sesion), onClick = onVolver)
    }
}

@Preview(showSystemUi = true)
@Composable
fun RecuperarContrasenaScreenPreview() {
    JaveWheelsTheme {
        RecuperarContrasenaScreen(onVolver = {}, onCodigoEnviado = {})
    }
}
