package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
import com.javeriana.javewheels.entities.DESTINO_JAVERIANA
import com.javeriana.javewheels.entities.Rol
import com.javeriana.javewheels.entities.Viaje
import com.javeriana.javewheels.entities.proximoViajeConductor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ============================================================
// Estado de las pantallas principales (Inicio, Perfil).
// Este ViewModel lo crea el NavHost y se comparte entre pantallas,
// porque el rol (pasajero / conductor) se usa en varias partes.
// ============================================================
data class InicioUiState(
    val rol: Rol = Rol.PASAJERO,
    val esConductorRegistrado: Boolean = false,  // true cuando registró su vehículo
    val destino: String = "",
    val viajeProgramado: Boolean = false,        // false = "Ahora", true = "Programar"
    val proximoViaje: Viaje = proximoViajeConductor
)

class InicioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InicioUiState())
    val uiState: StateFlow<InicioUiState> = _uiState.asStateFlow()

    /**
     * Cambia entre Pasajero y Conductor.
     * Devuelve false si todavía no tiene vehículo registrado
     * (en ese caso la UI lo manda a registrarlo).
     */
    fun cambiarRol(): Boolean {
        if (!_uiState.value.esConductorRegistrado) return false
        _uiState.update { estado ->
            val nuevoRol = if (estado.rol == Rol.PASAJERO) Rol.CONDUCTOR else Rol.PASAJERO
            estado.copy(rol = nuevoRol)
        }
        return true
    }

    /** Se llama al terminar el registro del vehículo. */
    fun activarPerfilConductor(rolInicial: Rol) {
        _uiState.update { it.copy(esConductorRegistrado = true, rol = rolInicial) }
    }

    /** Activa el modo pasajero. */
    fun usarComoPasajero() {
        _uiState.update { it.copy(rol = Rol.PASAJERO) }
    }

    // --- Panel "Buscar un Wheel" (pasajero) ---

    fun actualizarDestino(valor: String) {
        _uiState.update { it.copy(destino = valor) }
    }

    fun seleccionarMomento(programado: Boolean) {
        _uiState.update { it.copy(viajeProgramado = programado) }
    }

    fun irALaJaveriana() {
        _uiState.update { it.copy(destino = DESTINO_JAVERIANA) }
    }

    /** Devuelve el mensaje que la UI muestra en un Toast. */
    fun buscarWheels(): String {
        val estado = _uiState.value
        return if (estado.destino.isBlank()) {
            "Primero escribe tu destino."
        } else {
            val momento = if (estado.viajeProgramado) "programados" else "para ahora"
            "Buscando Wheels $momento hacia ${estado.destino}..."
        }
    }

    /** Vuelve todo al estado inicial (al cerrar sesión). */
    fun cerrarSesion() {
        _uiState.value = InicioUiState()
    }
}
