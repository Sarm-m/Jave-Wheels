package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
import com.javeriana.javewheels.R
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
    @androidx.annotation.StringRes val mensajeError: Int = 0
)

class RegistroViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun actualizarNombre(valor: String) {
        _uiState.update { it.copy(nombre = valor, mensajeError = 0) }
    }

    fun actualizarCorreo(valor: String) {
        _uiState.update { it.copy(correo = valor, mensajeError = 0) }
    }

    fun actualizarCelular(valor: String) {
        // Solo números y máximo 10 dígitos (celular colombiano: 3001234567)
        if (valor.length <= 10 && valor.all { it.isDigit() }) {
            _uiState.update { it.copy(celular = valor, mensajeError = 0) }
        }
    }

    fun actualizarContrasena(valor: String) {
        _uiState.update { it.copy(contrasena = valor, mensajeError = 0) }
    }

    fun actualizarConfirmarContrasena(valor: String) {
        _uiState.update { it.copy(confirmarContrasena = valor, mensajeError = 0) }
    }

    /** Valida el formulario. Devuelve true si la cuenta se puede "crear". */
    fun crearCuenta(): Boolean {
        val estado = _uiState.value
        val error = when {
            estado.nombre.isBlank() || estado.correo.isBlank() || estado.celular.isBlank() ||
                    estado.contrasena.isBlank() || estado.confirmarContrasena.isBlank() ->
                R.string.error_completa_todos_los_campos
            !esCorreoInstitucional(estado.correo) ->
                R.string.error_usa_tu_correo_institucional_javeriana_edu_co
            estado.celular.length != 10 ->
                R.string.error_el_numero_de_celular_debe_tener_10_digitos
            !esContrasenaValida(estado.contrasena) ->
                R.string.error_la_contrasena_debe_tener_minimo_8_caracteres_con_letras_y_numeros
            estado.contrasena != estado.confirmarContrasena ->
                R.string.error_las_contrasenas_no_coinciden
            else -> 0
        }
        _uiState.update { it.copy(mensajeError = error) }
        return error == 0
    }
}
