package com.javeriana.javewheels.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.javeriana.javewheels.entities.usuarioDePrueba
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
data class PerfilUiState(
    val nombre: String = usuarioDePrueba.nombre,
    val correo: String = usuarioDePrueba.correo,
    val fotoPerfil: Uri? = null,
    val nombreEditado: String = "",
    val correoEditado: String = "",
    val fotoEditada: Uri? = null,
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val mensajeError: String = ""
)

class PerfilViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    fun iniciarEdicion() {
        _uiState.update { estadoActual ->
            estadoActual.copy(
                nombreEditado = estadoActual.nombre,
                correoEditado = estadoActual.correo,
                fotoEditada = estadoActual.fotoPerfil,
                contrasena = "",
                confirmarContrasena = "",
                mensajeError = ""
            )
        }
    }

    fun actualizarNombre(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(nombreEditado = valor, mensajeError = "") }
    }

    fun actualizarCorreo(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(correoEditado = valor, mensajeError = "") }
    }

    fun actualizarFoto(nuevaFoto: Uri?) {
        _uiState.update { estadoActual -> estadoActual.copy(fotoEditada = nuevaFoto, mensajeError = "") }
    }

    fun actualizarContrasena(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(contrasena = valor, mensajeError = "") }
    }

    fun actualizarConfirmacion(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(confirmarContrasena = valor, mensajeError = "") }
    }

    /**
     * Valida lo editado. Si todo está bien lo convierte en datos guardados.
     * La contraseña es opcional: solo se valida si el usuario escribió algo
     * en la contraseña o en su confirmación.
     * Devuelve true si se guardó (la UI navega a la pantalla de éxito).
     */
    fun guardar(): Boolean {
        val estado = _uiState.value
        val cambiaContrasena = estado.contrasena.isNotBlank() || estado.confirmarContrasena.isNotBlank()
        val mensaje = when {
            !esNombreValido(estado.nombreEditado) ->
                "Escribe tu nombre completo."
            !esCorreoInstitucional(estado.correoEditado) ->
                "Usa tu correo institucional (@javeriana.edu.co)."
            cambiaContrasena && !esContrasenaValida(estado.contrasena) ->
                "La contraseña debe tener mínimo 8 caracteres, con letras y números."
            cambiaContrasena && estado.contrasena != estado.confirmarContrasena ->
                "Las contraseñas no coinciden."
            else -> ""
        }
        if (mensaje.isNotEmpty()) {
            _uiState.update { estadoActual -> estadoActual.copy(mensajeError = mensaje) }
            return false
        }
        _uiState.update { estadoActual ->
            estadoActual.copy(
                nombre = estadoActual.nombreEditado.trim(),
                correo = estadoActual.correoEditado.trim(),
                fotoPerfil = estadoActual.fotoEditada,
                contrasena = "",
                confirmarContrasena = "",
                mensajeError = ""
            )
        }
        return true
    }

    fun reiniciar() {
        _uiState.value = PerfilUiState()
    }
}