package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
import com.javeriana.javewheels.entities.paginasCarga
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ============================================================
// Estado de la pantalla de Carga (Carga 1, 2 y 3)
// Estado inmutable: se actualiza con copy().
// ============================================================
data class CargaUiState(
    val paginaActual: Int = 0,
    val totalPaginas: Int = paginasCarga.size
) {
    /** true cuando estamos en la última página (botón "Comenzar"). */
    val esUltimaPagina: Boolean
        get() = paginaActual == totalPaginas - 1
}

class CargaViewModel : ViewModel() {

    // _uiState = privado y MUTABLE (solo el ViewModel lo cambia)
    private val _uiState = MutableStateFlow(CargaUiState())

    // uiState = público y de SOLO LECTURA (la UI solo lo observa)
    val uiState: StateFlow<CargaUiState> = _uiState.asStateFlow()

    /** Avanza a la siguiente página si no es la última. */
    fun siguientePagina() {
        if (!_uiState.value.esUltimaPagina) {
            _uiState.update { estadoActual ->
                estadoActual.copy(paginaActual = estadoActual.paginaActual + 1)
            }
        }
    }

    /** Vuelve a la página anterior (flecha atrás de la carga). */
    fun paginaAnterior() {
        if (_uiState.value.paginaActual > 0) {
            _uiState.update { estadoActual ->
                estadoActual.copy(paginaActual = estadoActual.paginaActual - 1)
            }
        }
    }
}
