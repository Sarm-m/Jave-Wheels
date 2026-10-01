package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
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
    val mensajeError: String = ""
)

class VehiculoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VehiculoUiState())
    val uiState: StateFlow<VehiculoUiState> = _uiState.asStateFlow()

    fun actualizarPlaca(valor: String) {
        // La placa se guarda en mayúsculas (ABC123)
        _uiState.update { it.copy(placa = valor.uppercase(), mensajeError = "") }
    }

    fun actualizarMarca(valor: String) {
        _uiState.update { it.copy(marca = valor, mensajeError = "") }
    }

    fun actualizarModelo(valor: String) {
        _uiState.update { it.copy(modelo = valor, mensajeError = "") }
    }

    fun actualizarAnio(valor: String) {
        if (valor.length <= 4 && valor.all { it.isDigit() }) {
            _uiState.update { it.copy(anio = valor, mensajeError = "") }
        }
    }

    fun actualizarColor(valor: String) {
        _uiState.update { it.copy(color = valor, mensajeError = "") }
    }

    fun actualizarCupos(valor: String) {
        if (valor.length <= 1 && valor.all { it.isDigit() }) {
            _uiState.update { it.copy(cupos = valor, mensajeError = "") }
        }
    }

    /** Valida que todos los campos estén llenos. Devuelve true si se puede guardar. */
    fun guardar(): Boolean {
        val e = _uiState.value
        val camposVacios = listOf(e.placa, e.marca, e.modelo, e.anio, e.color, e.cupos)
            .any { it.isBlank() }
        val error = when {
            camposVacios -> "Completa todos los campos para continuar."
            e.cupos.toInt() == 0 -> "Debes ofrecer al menos 1 cupo."
            else -> ""
        }
        _uiState.update { it.copy(mensajeError = error) }
        return error.isEmpty()
    }
}
