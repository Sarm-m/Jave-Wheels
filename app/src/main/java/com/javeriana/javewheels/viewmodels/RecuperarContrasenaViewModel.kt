package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
import com.javeriana.javewheels.R
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
    @androidx.annotation.StringRes val mensajeError: Int = 0
)

class RecuperarContrasenaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecuperarUiState())
    val uiState: StateFlow<RecuperarUiState> = _uiState.asStateFlow()

    // ---------- Paso 1: correo ----------
    fun actualizarCorreo(valor: String) {
        _uiState.update { it.copy(correo = valor, mensajeError = 0) }
    }

    /** "Envía" el código. Devuelve true si el correo es válido. */
    fun enviarCodigo(): Boolean {
        val correo = _uiState.value.correo
        val error = if (esCorreoInstitucional(correo)) 0 else
            R.string.error_escribe_tu_correo_institucional_javeriana_edu_co
        _uiState.update { it.copy(mensajeError = error, codigo = "") }
        return error == 0
    }

    // ---------- Paso 2: código ----------
    fun actualizarCodigo(valor: String) {
        // Solo se aceptan números y máximo 6 dígitos
        if (valor.length <= 6 && valor.all { it.isDigit() }) {
            _uiState.update { it.copy(codigo = valor, mensajeError = 0) }
        }
    }

    /** Compara con el código quemado (123456). */
    fun verificarCodigo(): Boolean {
        val codigo = _uiState.value.codigo
        val error = when {
            codigo.length < 6 -> R.string.error_el_codigo_tiene_6_digitos
            codigo != CODIGO_VERIFICACION -> R.string.error_el_codigo_no_es_correcto_intentalo_de_nuevo
            else -> 0
        }
        _uiState.update { it.copy(mensajeError = error) }
        return error == 0
    }

    // ---------- Paso 3: nueva contraseña ----------
    fun actualizarNuevaContrasena(valor: String) {
        _uiState.update { it.copy(nuevaContrasena = valor, mensajeError = 0) }
    }

    fun actualizarConfirmarContrasena(valor: String) {
        _uiState.update { it.copy(confirmarContrasena = valor, mensajeError = 0) }
    }

    /** Valida la nueva contraseña. Si todo está bien, limpia el flujo. */
    fun restablecerContrasena(): Boolean {
        val estado = _uiState.value
        val error = when {
            !esContrasenaValida(estado.nuevaContrasena) ->
                R.string.error_usa_al_menos_8_caracteres_con_letras_y_numeros
            estado.nuevaContrasena != estado.confirmarContrasena ->
                R.string.error_las_contrasenas_no_coinciden
            else -> 0
        }
        if (error == 0) {
            // Terminó el flujo: se reinicia para la próxima vez
            _uiState.value = RecuperarUiState()
        } else {
            _uiState.update { it.copy(mensajeError = error) }
        }
        return error == 0
    }

}
