package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.AvatarUsuario
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.DialogoSeleccionFoto
import com.javeriana.javewheels.ui.components.FilaEditable
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.viewmodels.*
import com.javeriana.javewheels.viewmodels.PerfilViewModel

@Composable
fun EditarPerfilScreen(
    viewModel: PerfilViewModel,
    onGuardado: () -> Unit,
    modifier: Modifier = Modifier
) {
    val estado by viewModel.uiState.collectAsState()
    val focos = remember { List(4) { FocusRequester() } }
    var camposAbiertos by remember { mutableStateOf(emptySet<Int>()) }
    fun actualizarEdicion(indices: Set<Int>, abierto: Boolean) {
        camposAbiertos = if (abierto) camposAbiertos + indices else camposAbiertos - indices
    }
    fun siguiente(indice: Int): FocusRequester? = camposAbiertos.sorted()
        .firstOrNull { it > indice }?.let { focos[it] }

    // Estado visual: si el diálogo de la foto está abierto
    var mostrarDialogoFoto by remember { mutableStateOf(false) }

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
        Text(
            text = stringResource(R.string.ui_aqui_podras_editar_tus_datos),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        AvatarUsuario(
            foto = estado.fotoEditada,
            descripcion = stringResource(R.string.ui_foto_de_perfil),
            modifier = Modifier
                .size(170.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(12.dp))
        BotonPrincipal(
            texto = stringResource(R.string.ui_editar_foto),
            onClick = { mostrarDialogoFoto = true },
            compacto = true,
            colorFondo = MaterialTheme.colorScheme.outline,
            colorTexto = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        FilaEditable(
            solicitanteFoco = focos[0], siguienteFoco = siguiente(0),
            alCambiarEdicion = { actualizarEdicion(setOf(0), it) },
            etiqueta = stringResource(R.string.ui_nombre_completo),
            valor = estado.nombreEditado,
            hayError = estado.mensajeError != 0 && (!esNombreValido(estado.nombreEditado)),
            mensajeApoyo = if (estado.mensajeError != 0 && (!esNombreValido(estado.nombreEditado))) stringResource(R.string.error_escribe_tu_nombre_completo) else null,
            alCambiar = viewModel::actualizarNombre,
            textoEditar = stringResource(R.string.editar),
            textoListo = stringResource(R.string.listo),
            placeholder = stringResource(R.string.ui_escribe_tu_nombre_completo)
        )
        FilaEditable(
            solicitanteFoco = focos[1], siguienteFoco = siguiente(1),
            alCambiarEdicion = { actualizarEdicion(setOf(1), it) },
            etiqueta = stringResource(R.string.ui_correo_institucional),
            valor = estado.correoEditado,
            hayError = estado.mensajeError != 0 && (!esCorreoInstitucional(estado.correoEditado)),
            mensajeApoyo = if (estado.mensajeError != 0 && (!esCorreoInstitucional(estado.correoEditado))) stringResource(R.string.error_usa_tu_correo_institucional_javeriana_edu_co) else null,
            alCambiar = viewModel::actualizarCorreo,
            textoEditar = stringResource(R.string.editar),
            textoListo = stringResource(R.string.listo),
            placeholder = stringResource(R.string.ui_nombre_javeriana_edu_co),
            tipoTeclado = KeyboardType.Email
        )
        // La contraseña es opcional: solo se pide si el usuario toca Editar
        FilaEditable(
            solicitanteFoco = focos[2], siguienteFoco = focos[3],
            alCambiarEdicion = { actualizarEdicion(setOf(2, 3), it) },
            etiqueta = stringResource(R.string.ui_contrasena),
            etiquetaCampo = stringResource(R.string.ui_nueva_contrasena),
            valor = estado.contrasena,
            hayError = estado.mensajeError != 0 && ((estado.contrasena.isNotBlank() || estado.confirmarContrasena.isNotBlank()) && !esContrasenaValida(estado.contrasena)),
            mensajeApoyo = if (estado.mensajeError != 0 && ((estado.contrasena.isNotBlank() || estado.confirmarContrasena.isNotBlank()) && !esContrasenaValida(estado.contrasena))) stringResource(R.string.error_la_contrasena_debe_tener_minimo_8_caracteres_con_letras_y_numeros) else null,
            alCambiar = viewModel::actualizarContrasena,
            textoEditar = stringResource(R.string.editar),
            textoListo = stringResource(R.string.listo),
            placeholder = stringResource(R.string.ui_escribe_tu_nueva_contrasena),
            esContrasena = true,
            obligatorio = false,
            contenidoExtra = {
                Spacer(modifier = Modifier.height(12.dp))
                CampoTexto(solicitanteFoco = focos[3],
                    etiqueta = stringResource(R.string.ui_confirmar_contrasena),
                    valor = estado.confirmarContrasena,
            hayError = estado.mensajeError != 0 && (estado.confirmarContrasena != estado.contrasena),
            mensajeApoyo = if (estado.mensajeError != 0 && (estado.confirmarContrasena != estado.contrasena)) stringResource(R.string.error_las_contrasenas_no_coinciden) else null,
                    alCambiar = viewModel::actualizarConfirmacion,
                    placeholder = stringResource(R.string.ui_confirma_tu_contrasena),
                    esContrasena = true
                )
            }
        )

        MensajeError(mensaje = estado.mensajeError)
        Spacer(modifier = Modifier.height(12.dp))
        BotonPrincipal(
            texto = stringResource(R.string.guardar),
            onClick = { if (viewModel.guardar()) onGuardado() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(160.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }

    if (mostrarDialogoFoto) {
        DialogoSeleccionFoto(
            fotoActual = estado.fotoEditada,
            textoInstruccion = stringResource(R.string.ui_presiona_para_cargar_foto),
            textoBoton = stringResource(R.string.guardar),
            descripcionAvatar = stringResource(R.string.ui_foto_de_perfil),
            onGuardar = { fotoNueva ->
                viewModel.actualizarFoto(fotoNueva)
                mostrarDialogoFoto = false
            },
            onCerrar = { mostrarDialogoFoto = false }
        )
    }
}
