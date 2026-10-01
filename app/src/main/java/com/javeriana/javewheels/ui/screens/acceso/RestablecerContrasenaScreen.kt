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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BotonTexto
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.EncabezadoAcceso
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
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
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = "Crea una nueva\ncontraseña",
            descripcion = "Elige una contraseña diferente a la anterior.",
            onVolver = onVolver
        )
        Spacer(modifier = Modifier.height(24.dp))

        CampoTexto(
            etiqueta = "Nueva contraseña",
            valor = uiState.nuevaContrasena,
            alCambiar = { viewModel.actualizarNuevaContrasena(it) },
            placeholder = "Nueva contraseña",
            esContrasena = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto(
            etiqueta = "Confirmar contraseña",
            valor = uiState.confirmarContrasena,
            alCambiar = { viewModel.actualizarConfirmarContrasena(it) },
            placeholder = "Repite la contraseña",
            esContrasena = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextoAyuda(texto = "Usa al menos 8 caracteres, con letras y números.")
        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(16.dp))

        BotonPrincipal(
            texto = "Restablecer contraseña",
            onClick = { if (viewModel.restablecerContrasena()) onContrasenaRestablecida() }
        )
        Spacer(modifier = Modifier.height(32.dp))
        BotonTexto(texto = "Volver a iniciar sesión", onClick = onIrALogin)
    }
}

@Preview(showSystemUi = true)
@Composable
fun RestablecerContrasenaScreenPreview() {
    JaveWheelsTheme {
        RestablecerContrasenaScreen(onVolver = {}, onContrasenaRestablecida = {}, onIrALogin = {})
    }
}
