package com.javeriana.javewheels.ui.screens.acceso

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BotonTexto
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.EncabezadoAcceso
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.theme.JWCelesteSuave
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = "¿Olvidaste tu\ncontraseña?",
            descripcion = "Ingresa el correo asociado a tu cuenta.\nTe enviaremos un código para recuperarla.",
            onVolver = onVolver
        )
        Spacer(modifier = Modifier.height(24.dp))

        CampoTexto(
            etiqueta = "Correo institucional",
            valor = uiState.correo,
            alCambiar = { viewModel.actualizarCorreo(it) },
            placeholder = "nombre@javeriana.edu.co",
            tipoTeclado = KeyboardType.Email,
            hayError = uiState.mensajeError.isNotEmpty()
        )
        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(16.dp))
        BotonPrincipal(
            texto = "Enviar código",
            onClick = { if (viewModel.enviarCodigo()) onCodigoEnviado() }
        )
        Spacer(modifier = Modifier.height(32.dp))

        // "Ayuda con el correo"
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(JWCelesteSuave)
                .padding(16.dp)
        ) {
            Text(
                text = "¿No encuentras el mensaje?",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Revisa también la carpeta de correo no deseado.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        BotonTexto(texto = "Volver a iniciar sesión", onClick = onVolver)
    }
}

@Preview(showSystemUi = true)
@Composable
fun RecuperarContrasenaScreenPreview() {
    JaveWheelsTheme {
        RecuperarContrasenaScreen(onVolver = {}, onCodigoEnviado = {})
    }
}
