package com.javeriana.javewheels.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.javeriana.javewheels.R
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
    @androidx.annotation.StringRes val mensajeError: Int = 0
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
                mensajeError = 0
            )
        }
    }

    fun actualizarNombre(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(nombreEditado = valor, mensajeError = 0) }
    }

    fun actualizarCorreo(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(correoEditado = valor, mensajeError = 0) }
    }

    fun actualizarFoto(nuevaFoto: Uri?) {
        _uiState.update { estadoActual -> estadoActual.copy(fotoEditada = nuevaFoto, mensajeError = 0) }
    }

    fun actualizarContrasena(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(contrasena = valor, mensajeError = 0) }
    }

    fun actualizarConfirmacion(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(confirmarContrasena = valor, mensajeError = 0) }
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
                R.string.error_escribe_tu_nombre_completo
            !esCorreoInstitucional(estado.correoEditado) ->
                R.string.error_usa_tu_correo_institucional_javeriana_edu_co
            cambiaContrasena && !esContrasenaValida(estado.contrasena) ->
                R.string.error_la_contrasena_debe_tener_minimo_8_caracteres_con_letras_y_numeros
            cambiaContrasena && estado.contrasena != estado.confirmarContrasena ->
                R.string.error_las_contrasenas_no_coinciden
            else -> 0
        }
        if (mensaje != 0) {
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
                mensajeError = 0
            )
        }
        return true
    }

    fun reiniciar() {
        _uiState.value = PerfilUiState()
    }
}
