package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ============================================================
// Estado de "Acceso - Iniciar sesión"
// ============================================================
data class LoginUiState(
    val correo: String = "",
    val contrasena: String = "",
    val mensajeError: String = ""   // "" = no hay error
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // --- Eventos que llegan desde la UI (UI -> ViewModel) ---

    fun actualizarCorreo(nuevoCorreo: String) {
        _uiState.update { it.copy(correo = nuevoCorreo, mensajeError = "") }
    }

    fun actualizarContrasena(nuevaContrasena: String) {
        _uiState.update { it.copy(contrasena = nuevaContrasena, mensajeError = "") }
    }

    /**
     * Valida los datos. Como no hay backend, cualquier correo institucional
     * con contraseña válida "inicia sesión".
     * Devuelve true si se puede entrar; si no, deja un mensaje de error.
     */
    fun iniciarSesion(): Boolean {
        val estado = _uiState.value
        val error = when {
            estado.correo.isBlank() || estado.contrasena.isBlank() ->
                "Completa tu correo y tu contraseña."
            !esCorreoInstitucional(estado.correo) ->
                "Usa tu correo institucional (@javeriana.edu.co)."
            !esContrasenaValida(estado.contrasena) ->
                "La contraseña debe tener mínimo 8 caracteres, con letras y números."
            else -> ""
        }
        _uiState.update { it.copy(mensajeError = error) }
        return error.isEmpty()
    }

    /** Limpia el formulario (al cerrar sesión). */
    fun limpiar() {
        _uiState.value = LoginUiState()
    }
}
