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
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BotonTexto
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.EncabezadoAcceso
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.*
import com.javeriana.javewheels.viewmodels.RegistroViewModel

// ============================================================
// "Acceso - Registrarse"
// ============================================================

@Composable
fun RegistroScreen(
    onVolver: () -> Unit,
    onCuentaCreada: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegistroViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize().imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EncabezadoAcceso(
            titulo = stringResource(R.string.ui_crea_tu_cuenta),
            descripcion = stringResource(R.string.ui_unete_a_la_comunidad_javewheels),
            onVolver = onVolver
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextoAyuda(stringResource(R.string.campos_obligatorios))

        // Formulario de registro
        CampoTexto(
            etiqueta = stringResource(R.string.ui_nombre_completo),
            valor = uiState.nombre,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && (uiState.nombre.isBlank()),
            mensajeApoyo = if (uiState.mensajeError != 0 && (uiState.nombre.isBlank())) stringResource(R.string.error_escribe_tu_nombre_completo) else null,
            alCambiar = { viewModel.actualizarNombre(it) },
            placeholder = stringResource(R.string.ui_ej_maria_perez)
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            etiqueta = stringResource(R.string.ui_correo_institucional),
            valor = uiState.correo,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && (!esCorreoInstitucional(uiState.correo)),
            mensajeApoyo = if (uiState.mensajeError != 0 && (!esCorreoInstitucional(uiState.correo))) stringResource(R.string.error_usa_tu_correo_institucional_javeriana_edu_co) else null,
            alCambiar = { viewModel.actualizarCorreo(it) },
            placeholder = stringResource(R.string.ui_nombre_javeriana_edu_co),
            tipoTeclado = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            etiqueta = stringResource(R.string.ui_numero_de_celular),
            valor = uiState.celular,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && (uiState.celular.length != 10),
            mensajeApoyo = if (uiState.mensajeError != 0 && (uiState.celular.length != 10)) stringResource(R.string.error_el_numero_de_celular_debe_tener_10_digitos) else null,
            alCambiar = { viewModel.actualizarCelular(it) },
            placeholder = stringResource(R.string.ui_ej_3001234567),
            tipoTeclado = KeyboardType.Phone
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            etiqueta = stringResource(R.string.ui_contrasena),
            valor = uiState.contrasena,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && (!esContrasenaValida(uiState.contrasena)),
            mensajeApoyo = if (uiState.mensajeError != 0 && (!esContrasenaValida(uiState.contrasena))) stringResource(R.string.error_la_contrasena_debe_tener_minimo_8_caracteres_con_letras_y_numeros) else null,
            alCambiar = { viewModel.actualizarContrasena(it) },
            placeholder = stringResource(R.string.ui_crea_una_contrasena),
            esContrasena = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        CampoTexto(
            etiqueta = stringResource(R.string.ui_confirmar_contrasena),
            valor = uiState.confirmarContrasena,
            obligatorio = true,
            hayError = uiState.mensajeError != 0 && (uiState.confirmarContrasena.isBlank() || uiState.confirmarContrasena != uiState.contrasena),
            mensajeApoyo = if (uiState.mensajeError != 0 && (uiState.confirmarContrasena.isBlank() || uiState.confirmarContrasena != uiState.contrasena)) stringResource(R.string.error_las_contrasenas_no_coinciden) else null,
            alCambiar = { viewModel.actualizarConfirmarContrasena(it) },
            placeholder = stringResource(R.string.ui_repite_la_contrasena),
            esContrasena = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextoAyuda(texto = stringResource(R.string.ui_minimo_8_caracteres_con_letras_y_numeros))

        MensajeError(mensaje = uiState.mensajeError)
        Spacer(modifier = Modifier.height(12.dp))

        BotonPrincipal(
            texto = stringResource(R.string.ui_crear_cuenta),
            onClick = { if (viewModel.crearCuenta()) onCuentaCreada() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        BotonTexto(texto = stringResource(R.string.ui_ya_tienes_una_cuenta_inicia_sesion), onClick = onVolver)
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegistroScreenPreview() {
    JaveWheelsTheme {
        RegistroScreen(onVolver = {}, onCuentaCreada = {})
    }
}
