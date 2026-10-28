package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
import com.javeriana.javewheels.R
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
    @androidx.annotation.StringRes val mensajeError: Int = 0   // 0 = no hay error
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // --- Eventos que llegan desde la UI (UI -> ViewModel) ---

    fun actualizarCorreo(nuevoCorreo: String) {
        _uiState.update { it.copy(correo = nuevoCorreo, mensajeError = 0) }
    }

    fun actualizarContrasena(nuevaContrasena: String) {
        _uiState.update { it.copy(contrasena = nuevaContrasena, mensajeError = 0) }
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
                R.string.error_completa_tu_correo_y_tu_contrasena
            !esCorreoInstitucional(estado.correo) ->
                R.string.error_usa_tu_correo_institucional_javeriana_edu_co
            !esContrasenaValida(estado.contrasena) ->
                R.string.error_la_contrasena_debe_tener_minimo_8_caracteres_con_letras_y_numeros
            else -> 0
        }
        _uiState.update { it.copy(mensajeError = error) }
        return error == 0
    }

    /** Limpia el formulario (al cerrar sesión). */
    fun limpiar() {
        _uiState.value = LoginUiState()
    }
}
