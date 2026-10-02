package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
import com.javeriana.javewheels.entities.CODIGO_VERIFICACION
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ============================================================
// Estado del flujo de recuperación de contraseña.
// Un solo ViewModel para las 3 pantallas del flujo:
//   Recuperar (correo) -> Verificar código -> Restablecer contraseña
// Así el correo escrito en la primera pantalla sigue disponible
// en las siguientes.
// ============================================================
data class RecuperarUiState(
    val correo: String = "",
    val codigo: String = "",
    val nuevaContrasena: String = "",
    val confirmarContrasena: String = "",
    val mensajeError: String = ""
)

class RecuperarContrasenaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecuperarUiState())
    val uiState: StateFlow<RecuperarUiState> = _uiState.asStateFlow()

    // ---------- Paso 1: correo ----------
    fun actualizarCorreo(valor: String) {
        _uiState.update { it.copy(correo = valor, mensajeError = "") }
    }

    /** "Envía" el código. Devuelve true si el correo es válido. */
    fun enviarCodigo(): Boolean {
        val correo = _uiState.value.correo
        val error = if (esCorreoInstitucional(correo)) "" else
            "Escribe tu correo institucional (@javeriana.edu.co)."
        _uiState.update { it.copy(mensajeError = error, codigo = "") }
        return error.isEmpty()
    }

    // ---------- Paso 2: código ----------
    fun actualizarCodigo(valor: String) {
        // Solo se aceptan números y máximo 6 dígitos
        if (valor.length <= 6 && valor.all { it.isDigit() }) {
            _uiState.update { it.copy(codigo = valor, mensajeError = "") }
        }
    }

    /** Compara con el código quemado (123456). */
    fun verificarCodigo(): Boolean {
        val codigo = _uiState.value.codigo
        val error = when {
            codigo.length < 6 -> "El código tiene 6 dígitos."
            codigo != CODIGO_VERIFICACION -> "El código no es correcto. Inténtalo de nuevo."
            else -> ""
        }
        _uiState.update { it.copy(mensajeError = error) }
        return error.isEmpty()
    }

    // ---------- Paso 3: nueva contraseña ----------
    fun actualizarNuevaContrasena(valor: String) {
        _uiState.update { it.copy(nuevaContrasena = valor, mensajeError = "") }
    }

    fun actualizarConfirmarContrasena(valor: String) {
        _uiState.update { it.copy(confirmarContrasena = valor, mensajeError = "") }
    }

    /** Valida la nueva contraseña. Si todo está bien, limpia el flujo. */
    fun restablecerContrasena(): Boolean {
        val estado = _uiState.value
        val error = when {
            !esContrasenaValida(estado.nuevaContrasena) ->
                "Usa al menos 8 caracteres, con letras y números."
            estado.nuevaContrasena != estado.confirmarContrasena ->
                "Las contraseñas no coinciden."
            else -> ""
        }
        if (error.isEmpty()) {
            // Terminó el flujo: se reinicia para la próxima vez
            _uiState.value = RecuperarUiState()
        } else {
            _uiState.update { it.copy(mensajeError = error) }
        }
        return error.isEmpty()
    }

    /** Limpia el mensaje de error al volver atrás. */
    fun limpiarError() {
        _uiState.update { it.copy(mensajeError = "") }
    }
}
