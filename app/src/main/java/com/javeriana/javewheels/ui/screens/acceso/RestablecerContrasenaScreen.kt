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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BotonTexto
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.EncabezadoAcceso
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.*
import com.javeriana.javewheels.viewmodels.RecuperarContrasenaViewModel

// ============================================================
// "Acceso - Restablecer contraseña".
// ============================================================

@Composable
fun RestablecerContrasenaScreen(
    onVolver: () -> Unit,
    onContrasenaRestablecida: () -> Unit,
    onIrALogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecuperarContrasenaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize().imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = stringResource(R.string.ui_crea_una_nueva_contrasena),
            descripcion = stringResource(R.string.ui_elige_una_contrasena_diferente_a_la_anterior),
            onVolver = onVolver
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextoAyuda(stringResource(R.string.campos_obligatorios))

        CampoTexto(
            etiqueta = stringResource(R.string.ui_nueva_contrasena),
            valor = uiState.nuevaContrasena,
            hayError = uiState.mensajeError != 0 && (!esContrasenaValida(uiState.nuevaContrasena)),
            mensajeApoyo = if (uiState.mensajeError != 0 && (!esContrasenaValida(uiState.nuevaContrasena))) stringResource(R.string.error_usa_al_menos_8_caracteres_con_letras_y_numeros) else null,
            alCambiar = { viewModel.actualizarNuevaContrasena(it) },
            placeholder = stringResource(R.string.ui_nueva_contrasena),
            esContrasena = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto(
            etiqueta = stringResource(R.string.ui_confirmar_contrasena),
            valor = uiState.confirmarContrasena,
            hayError = uiState.mensajeError != 0 && (uiState.confirmarContrasena != uiState.nuevaContrasena),
            mensajeApoyo = if (uiState.mensajeError != 0 && (uiState.confirmarContrasena != uiState.nuevaContrasena)) stringResource(R.string.error_las_contrasenas_no_coinciden) else null,
            alCambiar = { viewModel.actualizarConfirmarContrasena(it) },
            placeholder = stringResource(R.string.ui_repite_la_contrasena),
            esContrasena = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextoAyuda(texto = stringResource(R.string.error_usa_al_menos_8_caracteres_con_letras_y_numeros))
        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(16.dp))

        BotonPrincipal(
            texto = stringResource(R.string.ui_restablecer_contrasena),
            onClick = { if (viewModel.restablecerContrasena()) onContrasenaRestablecida() }
        )
        Spacer(modifier = Modifier.height(32.dp))
        BotonTexto(texto = stringResource(R.string.ui_volver_a_iniciar_sesion), onClick = onIrALogin)
    }
}

@Preview(showSystemUi = true)
@Composable
fun RestablecerContrasenaScreenPreview() {
    JaveWheelsTheme {
        RestablecerContrasenaScreen(onVolver = {}, onContrasenaRestablecida = {}, onIrALogin = {})
    }
}
