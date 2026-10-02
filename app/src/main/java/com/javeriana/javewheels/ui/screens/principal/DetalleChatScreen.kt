package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.BurbujaMensaje
import com.javeriana.javewheels.ui.components.CampoTexto
import com.javeriana.javewheels.ui.components.FilaConAvatar
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.ui.theme.JWCelesteContenedor
import com.javeriana.javewheels.viewmodels.ChatsViewModel

@Composable
fun DetalleChatScreen(
    viewModel: ChatsViewModel,
    idChat: Int,
    modifier: Modifier = Modifier
) {
    val estado by viewModel.uiState.collectAsState()
    val chat = estado.chats.find { chatActual -> chatActual.id == idChat }
    val estadoLista = rememberLazyListState()

    LaunchedEffect(chat?.mensajes?.size) {
        val cantidad = chat?.mensajes?.size ?: 0
        if (cantidad > 0) estadoLista.animateScrollToItem(cantidad - 1)
    }

    if (chat != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .imePadding()   // sube la caja de texto cuando sale el teclado
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            MarcaJaveWheels()
            Spacer(modifier = Modifier.height(16.dp))

            // Encabezado: el usuario del otro lado del chat
            FilaConAvatar(
                titulo = chat.nombreUsuario,
                subtitulo = if (idChat in estado.chatsEscribiendo) "Escribiendo..." else "",
                descripcionAvatar = "Foto de ${chat.nombreUsuario}",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(JWCelesteContenedor)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Mensajes
            LazyColumn(
                state = estadoLista,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(chat.mensajes) { mensaje ->
                    BurbujaMensaje(texto = mensaje.texto, esMio = mensaje.esMio)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CampoTexto(
                    etiqueta = "",
                    valor = estado.borrador,
                    alCambiar = viewModel::actualizarBorrador,
                    placeholder = "Escribe un mensaje . . .",
                    modifier = Modifier.weight(1f)
                )
                BotonPrincipal(
                    texto = "Enviar",
                    onClick = { viewModel.enviarMensaje(idChat) },
                    modifier = Modifier.width(96.dp)
                )
            }
        }
    }
}