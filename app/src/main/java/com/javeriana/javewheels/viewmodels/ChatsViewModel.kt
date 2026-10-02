package com.javeriana.javewheels.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javeriana.javewheels.entities.Chat
import com.javeriana.javewheels.entities.Mensaje
import com.javeriana.javewheels.entities.TIEMPO_RESPUESTA_CHAT
import com.javeriana.javewheels.entities.chatsQuemados
import com.javeriana.javewheels.entities.respuestasQuemadas
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatsUiState(
    val chats: List<Chat> = chatsQuemados,
    val busqueda: String = "",
    val borrador: String = "",                       // mensaje que se está escribiendo
    val chatsEscribiendo: Set<Int> = emptySet()      // chats donde el otro usuario "está escribiendo"
) {
    val chatsFiltrados: List<Chat>
        get() {
            val texto = busqueda.trim()
            if (texto.isEmpty()) return chats
            return chats.filter { chat ->
                chat.nombreUsuario.contains(texto, ignoreCase = true) ||
                        chat.mensajes.any { mensaje -> mensaje.texto.contains(texto, ignoreCase = true) }
            }
        }
}

class ChatsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatsUiState())
    val uiState: StateFlow<ChatsUiState> = _uiState.asStateFlow()

    private val respuestasPendientes = mutableMapOf<Int, Job>()

    fun actualizarBusqueda(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(busqueda = valor) }
    }

    fun actualizarBorrador(valor: String) {
        _uiState.update { estadoActual -> estadoActual.copy(borrador = valor) }
    }

    /** Reutiliza el chat del conductor y lo deja primero en Mensajes. */
    fun abrirChatConductor(nombre: String): Int {
        val chats = _uiState.value.chats
        val chat = chats.firstOrNull { it.nombreUsuario.equals(nombre, ignoreCase = true) }
            ?: Chat(
                id = (chats.maxOfOrNull { it.id } ?: 0) + 1,
                nombreUsuario = nombre,
                mensajes = emptyList()
            )
        _uiState.update { estadoActual ->
            estadoActual.copy(
                chats = listOf(chat) + estadoActual.chats.filter { it.id != chat.id },
                busqueda = "",
                borrador = ""
            )
        }
        return chat.id
    }

    fun enviarMensaje(idChat: Int) {
        val texto = _uiState.value.borrador.trim()
        if (texto.isEmpty()) return
        agregarMensaje(idChat, Mensaje(texto = texto, esMio = true))
        _uiState.update { estadoActual -> estadoActual.copy(borrador = "") }
        responderComoOtroUsuario(idChat)
    }

    private fun agregarMensaje(idChat: Int, mensaje: Mensaje) {
        _uiState.update { estadoActual ->
            estadoActual.copy(
                chats = estadoActual.chats.map { chat ->
                    if (chat.id == idChat) chat.copy(mensajes = chat.mensajes + mensaje) else chat
                }
            )
        }
    }

//simulacion de usuario
    private fun responderComoOtroUsuario(idChat: Int) {
        respuestasPendientes[idChat]?.cancel()
        respuestasPendientes[idChat] = viewModelScope.launch {
            _uiState.update { estadoActual ->
                estadoActual.copy(chatsEscribiendo = estadoActual.chatsEscribiendo + idChat)
            }
            delay(TIEMPO_RESPUESTA_CHAT)
            val chat = _uiState.value.chats.find { chatActual -> chatActual.id == idChat }
                ?: return@launch
            val respuesta = respuestasQuemadas[chat.mensajes.size % respuestasQuemadas.size]
            agregarMensaje(idChat, Mensaje(texto = respuesta, esMio = false))
            _uiState.update { estadoActual ->
                estadoActual.copy(chatsEscribiendo = estadoActual.chatsEscribiendo - idChat)
            }
        }
    }

    /** Vuelve todo al estado inicial (al cerrar sesión). */
    fun reiniciar() {
        respuestasPendientes.values.forEach { trabajo -> trabajo.cancel() }
        respuestasPendientes.clear()
        _uiState.value = ChatsUiState()
    }
}
