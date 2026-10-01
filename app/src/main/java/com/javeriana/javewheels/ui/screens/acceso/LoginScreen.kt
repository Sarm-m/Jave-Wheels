package com.javeriana.javewheels.ui.screens.acceso

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BotonSecundario
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.EncabezadoAcceso
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.LoginViewModel

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
    val hayError = uiState.mensajeError.isNotEmpty()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = "¡Hola, javeriano!",
            descripcion = "Tu próximo Wheel empieza aquí."
        )
        Spacer(modifier = Modifier.height(28.dp))

        // Formulario de acceso
        CampoTexto(
            etiqueta = "Correo institucional",
            valor = uiState.correo,
            alCambiar = { viewModel.actualizarCorreo(it) },   // evento -> ViewModel
            placeholder = "nombre@javeriana.edu.co",
            tipoTeclado = KeyboardType.Email,
            hayError = hayError
        )
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto(
            etiqueta = "Contraseña",
            valor = uiState.contrasena,
            alCambiar = { viewModel.actualizarContrasena(it) },
            placeholder = "Tu contraseña",
            esContrasena = true,
            hayError = hayError
        )

        // "¿Olvidaste tu contraseña?" alineado a la derecha
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(onClick = onOlvidoContrasena) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(12.dp))

        // Acciones de acceso
        BotonPrincipal(
            texto = "Iniciar sesión",
            onClick = {
                // El ViewModel valida; si todo está bien, se navega
                if (viewModel.iniciarSesion()) onIniciarSesion()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextoAyuda(texto = "¿Primera vez en JaveWheels?", centrado = true)
        Spacer(modifier = Modifier.height(12.dp))
        BotonSecundario(texto = "Crear una cuenta", onClick = onCrearCuenta)

        Spacer(modifier = Modifier.height(40.dp))
        TextoAyuda(texto = "Movilidad para la comunidad Javeriana", centrado = true)
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    JaveWheelsTheme {
        LoginScreen(onIniciarSesion = {}, onOlvidoContrasena = {}, onCrearCuenta = {})
    }
}
