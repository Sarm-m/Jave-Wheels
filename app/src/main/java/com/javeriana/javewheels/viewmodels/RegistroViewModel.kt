package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ============================================================
// Estado de "Acceso - Registrarse"
// ============================================================
data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val celular: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val mensajeError: String = ""
)

class RegistroViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun actualizarNombre(valor: String) {
        _uiState.update { it.copy(nombre = valor, mensajeError = "") }
    }

    fun actualizarCorreo(valor: String) {
        _uiState.update { it.copy(correo = valor, mensajeError = "") }
    }

    fun actualizarCelular(valor: String) {
        // Solo números y máximo 10 dígitos (celular colombiano: 3001234567)
        if (valor.length <= 10 && valor.all { it.isDigit() }) {
            _uiState.update { it.copy(celular = valor, mensajeError = "") }
        }
    }

    fun actualizarContrasena(valor: String) {
        _uiState.update { it.copy(contrasena = valor, mensajeError = "") }
    }

    fun actualizarConfirmarContrasena(valor: String) {
        _uiState.update { it.copy(confirmarContrasena = valor, mensajeError = "") }
    }

    /** Valida el formulario. Devuelve true si la cuenta se puede "crear". */
    fun crearCuenta(): Boolean {
        val estado = _uiState.value
        val error = when {
            estado.nombre.isBlank() || estado.correo.isBlank() || estado.celular.isBlank() ||
                    estado.contrasena.isBlank() || estado.confirmarContrasena.isBlank() ->
                "Completa todos los campos."
            !esCorreoInstitucional(estado.correo) ->
                "Usa tu correo institucional (@javeriana.edu.co)."
            estado.celular.length != 10 ->
                "El número de celular debe tener 10 dígitos."
            !esContrasenaValida(estado.contrasena) ->
                "La contraseña debe tener mínimo 8 caracteres, con letras y números."
            estado.contrasena != estado.confirmarContrasena ->
                "Las contraseñas no coinciden."
            else -> ""
        }
        _uiState.update { it.copy(mensajeError = error) }
        return error.isEmpty()
    }
}
