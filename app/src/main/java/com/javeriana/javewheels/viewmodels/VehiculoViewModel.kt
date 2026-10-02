package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
import com.javeriana.javewheels.entities.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ============================================================
// Estado de "Conductor - Registrar vehículo"
// ============================================================
data class VehiculoUiState(
    val placa: String = "",
    val marca: String = "",
    val modelo: String = "",
    val anio: String = "",
    val color: String = "",
    val cupos: String = "",
    val celular: String = "", //c usa en caso de editar
    val mensajeError: String = ""
) {
    val resumen: String
        get() = listOf(marca, modelo, anio, color, placa)
            .map { it.trim() }.filter { it.isNotBlank() }.joinToString(" · ")
}

class VehiculoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VehiculoUiState())
    val uiState: StateFlow<VehiculoUiState> = _uiState.asStateFlow()

    fun actualizarPlaca(valor: String) {
        // La placa se guarda en mayúsculas (ABC123), max 6 char
        if (valor.length <= 6) {
            _uiState.update { estadoActual -> estadoActual.copy(placa = valor.uppercase(), mensajeError = "") }
        }
    }

    fun actualizarMarca(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(marca = valor, mensajeError = "") }
    }

    fun actualizarModelo(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(modelo = valor, mensajeError = "") }
    }

    fun actualizarAnio(valor: String) {
        if (valor.length <= 4 && valor.all { caracter -> caracter.isDigit() }) {
            _uiState.update { estadoActual -> estadoActual.copy(anio = valor, mensajeError = "") }
        }
    }

    fun actualizarColor(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(color = valor, mensajeError = "") }
    }

    fun actualizarCupos(valor: String) {
        if (valor.length <= 1 && valor.all { caracter -> caracter.isDigit() }) {
            _uiState.update { estadoActual -> estadoActual.copy(cupos = valor, mensajeError = "") }
        }
    }

    fun actualizarCelular(valor: String) {
        if (valor.length <= 10 && valor.all { caracter -> caracter.isDigit() }) {
            _uiState.update { estadoActual -> estadoActual.copy(celular = valor, mensajeError = "") }
        }
    }

    fun cargarParaEdicion(usuario: Usuario) {
        _uiState.update { estadoActual ->
            estadoActual.copy(
                placa = estadoActual.placa.ifBlank { usuario.placa.orEmpty() },
                marca = estadoActual.marca.ifBlank { usuario.marca.orEmpty() },
                modelo = estadoActual.modelo.ifBlank { usuario.modelo.orEmpty() },
                anio = estadoActual.anio.ifBlank { usuario.anio?.toString().orEmpty() },
                color = estadoActual.color.ifBlank { usuario.color.orEmpty() },
                cupos = estadoActual.cupos.ifBlank { usuario.cupos?.toString().orEmpty() },
                celular = estadoActual.celular.ifBlank {
                    usuario.celular.filter { caracter -> caracter.isDigit() }
                },
                mensajeError = ""
            )
        }
    }

    fun guardar(): Boolean = validar(exigirCelular = false)
    fun guardarEdicion(): Boolean = validar(exigirCelular = true)

    private fun validar(exigirCelular: Boolean): Boolean {
        val estado = _uiState.value
        val camposVacios = listOf(estado.placa, estado.marca, estado.modelo, estado.anio, estado.color, estado.cupos)
            .any { campo -> campo.isBlank() }
        val mensaje = when {
            camposVacios -> "Completa todos los campos para continuar."
            !esPlacaValida(estado.placa) -> "La placa debe tener 3 letras y 3 números (ej. ABC123)."
            !esAnioValido(estado.anio) -> "Escribe un año válido del vehículo."
            estado.cupos.toInt() == 0 -> "Debes ofrecer al menos 1 cupo."
            exigirCelular && !esCelularValido(estado.celular) -> "Escribe un celular válido de 10 dígitos."
            else -> ""
        }
        _uiState.update { estadoActual -> estadoActual.copy(mensajeError = mensaje) }
        return mensaje.isEmpty()
    }

    fun reiniciar() {
        _uiState.value = VehiculoUiState()
    }
}
