package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.FilaConAvatar
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.ui.components.TextoAyuda
import com.javeriana.javewheels.viewmodels.ChatsViewModel

@Composable
fun ListaChatsScreen(
    viewModel: ChatsViewModel,
    onAbrirChat: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val estado by viewModel.uiState.collectAsState()
    val chatsFiltrados = estado.chatsFiltrados

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        MarcaJaveWheels()
        Spacer(modifier = Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "",
            valor = estado.busqueda,
            alCambiar = viewModel::actualizarBusqueda,
            placeholder = "Buscar chat",
            iconoFinal = Icons.Default.Search,
            descripcionIconoFinal = "Buscar"
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (chatsFiltrados.isEmpty()) {
            TextoAyuda(
                texto = "No hay chats que coincidan con tu búsqueda.",
                centrado = true,
                modifier = Modifier.padding(top = 24.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(chatsFiltrados, key = { chat -> chat.id }) { chat ->
                    val ultimoMensaje = chat.ultimoMensaje
                    val vistaPrevia = when {
                        chat.id in estado.chatsEscribiendo -> "Escribiendo..."
                        ultimoMensaje == null -> ""
                        ultimoMensaje.esMio -> "Tú: ${ultimoMensaje.texto}"
                        else -> ultimoMensaje.texto
                    }
                    FilaConAvatar(
                        titulo = chat.nombreUsuario,
                        subtitulo = vistaPrevia,
                        descripcionAvatar = "Foto de ${chat.nombreUsuario}",
                        onClick = { onAbrirChat(chat.id) }
                    )
                }
            }
        }
    }
}