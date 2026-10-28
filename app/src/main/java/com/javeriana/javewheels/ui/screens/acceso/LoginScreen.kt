package com.javeriana.javewheels.ui.screens.acceso

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BotonSecundario
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.EncabezadoAcceso
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.LoginViewModel
import com.javeriana.javewheels.viewmodels.esContrasenaValida
import com.javeriana.javewheels.viewmodels.esCorreoInstitucional

// ============================================================
// "Acceso - Iniciar sesión".
// ============================================================

@Composable
fun LoginScreen(
    onIniciarSesion: () -> Unit,
    onOlvidoContrasena: () -> Unit,
    onCrearCuenta: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val hayError = uiState.mensajeError != 0

    Column(
        modifier = modifier
            .fillMaxSize().imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = stringResource(R.string.ui_hola_javeriano),
            descripcion = stringResource(R.string.ui_tu_proximo_wheel_empieza_aqui)
        )
        Spacer(modifier = Modifier.height(28.dp))

        // Formulario de acceso
        CampoTexto(
            etiqueta = stringResource(R.string.ui_correo_institucional),
            valor = uiState.correo,
            alCambiar = { viewModel.actualizarCorreo(it) },   // evento -> ViewModel
            placeholder = stringResource(R.string.ui_nombre_javeriana_edu_co),
            tipoTeclado = KeyboardType.Email,
            hayError = hayError && !esCorreoInstitucional(uiState.correo)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto(
            etiqueta = stringResource(R.string.ui_contrasena),
            valor = uiState.contrasena,
            alCambiar = { viewModel.actualizarContrasena(it) },
            placeholder = stringResource(R.string.ui_tu_contrasena),
            esContrasena = true,
            hayError = hayError && !esContrasenaValida(uiState.contrasena)
        )

        // "¿Olvidaste tu contraseña?" alineado a la derecha
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(onClick = onOlvidoContrasena) {
                Text(
                    text = stringResource(R.string.ui_olvidaste_tu_contrasena),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(12.dp))

        // Acciones de acceso
        BotonPrincipal(
            texto = stringResource(R.string.ui_iniciar_sesion),
            onClick = {
                // El ViewModel valida; si todo está bien, se navega
                if (viewModel.iniciarSesion()) onIniciarSesion()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextoAyuda(texto = stringResource(R.string.ui_primera_vez_en_javewheels), centrado = true)
        Spacer(modifier = Modifier.height(12.dp))
        BotonSecundario(texto = stringResource(R.string.ui_crear_una_cuenta), onClick = onCrearCuenta)

        Spacer(modifier = Modifier.height(40.dp))
        TextoAyuda(texto = stringResource(R.string.ui_movilidad_para_la_comunidad_javeriana), centrado = true)
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    JaveWheelsTheme {
        LoginScreen(onIniciarSesion = {}, onOlvidoContrasena = {}, onCrearCuenta = {})
    }
}
