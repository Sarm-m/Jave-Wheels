package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.components.AvatarUsuario
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.DialogoSeleccionFoto
import com.javeriana.javewheels.ui.components.FilaEditable
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.ui.theme.JWAzul
import com.javeriana.javewheels.ui.theme.JWCeleste
import com.javeriana.javewheels.viewmodels.PerfilViewModel


@Composable
fun EditarPerfilScreen(
    viewModel: PerfilViewModel,
    onGuardado: () -> Unit,
    modifier: Modifier = Modifier
) {
    val estado by viewModel.uiState.collectAsState()
    // Estado visual: si el diálogo de la foto está abierto
    var mostrarDialogoFoto by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        MarcaJaveWheels()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¡Hola, javeriano!",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Aquí podrás editar tus datos",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        AvatarUsuario(
            foto = estado.fotoEditada,
            descripcion = "Foto de perfil",
            modifier = Modifier
                .size(170.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(12.dp))
        BotonPrincipal(
            texto = "Editar foto",
            onClick = { mostrarDialogoFoto = true },
            compacto = true,
            colorFondo = JWCeleste,
            colorTexto = JWAzul,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        FilaEditable(
            etiqueta = "Nombre completo",
            valor = estado.nombreEditado,
            alCambiar = viewModel::actualizarNombre,
            textoEditar = "Editar",
            textoListo = "Listo",
            placeholder = "Escribe tu nombre completo"
        )
        FilaEditable(
            etiqueta = "Correo institucional",
            valor = estado.correoEditado,
            alCambiar = viewModel::actualizarCorreo,
            textoEditar = "Editar",
            textoListo = "Listo",
            placeholder = "nombre@javeriana.edu.co",
            tipoTeclado = KeyboardType.Email
        )
        // La contraseña es opcional: solo se pide si el usuario toca Editar
        FilaEditable(
            etiqueta = "Contraseña",
            etiquetaCampo = "Nueva contraseña",
            valor = estado.contrasena,
            alCambiar = viewModel::actualizarContrasena,
            textoEditar = "Editar",
            textoListo = "Listo",
            placeholder = "Escribe tu nueva contraseña",
            esContrasena = true,
            obligatorio = false,
            contenidoExtra = {
                Spacer(modifier = Modifier.height(12.dp))
                CampoTexto(
                    etiqueta = "Confirmar contraseña",
                    valor = estado.confirmarContrasena,
                    alCambiar = viewModel::actualizarConfirmacion,
                    placeholder = "Confirma tu contraseña",
                    esContrasena = true
                )
            }
        )

        MensajeError(mensaje = estado.mensajeError)
        Spacer(modifier = Modifier.height(12.dp))
        BotonPrincipal(
            texto = "Guardar",
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
            textoInstruccion = "Presiona para cargar foto",
            textoBoton = "Guardar",
            descripcionAvatar = "Foto de perfil",
            onGuardar = { fotoNueva ->
                viewModel.actualizarFoto(fotoNueva)
                mostrarDialogoFoto = false
            },
            onCerrar = { mostrarDialogoFoto = false }
        )
    }
}