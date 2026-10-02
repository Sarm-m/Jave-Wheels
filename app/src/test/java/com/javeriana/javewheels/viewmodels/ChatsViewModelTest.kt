package com.javeriana.javewheels.viewmodels

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ChatsViewModelTest {
    @Test
    fun conductorNuevoAparecePrimeroSinCambiarLosOtrosChats() {
        val viewModel = ChatsViewModel()
        val chatsAnteriores = viewModel.uiState.value.chats
        viewModel.actualizarBusqueda("Camila")
        viewModel.actualizarBorrador("Borrador de otro chat")

        val idChat = viewModel.abrirChatConductor("Alexandra Ramos")
        val estado = viewModel.uiState.value

        assertEquals(idChat, estado.chats.first().id)
        assertEquals("Alexandra Ramos", estado.chats.first().nombreUsuario)
        assertTrue(estado.chats.first().mensajes.isEmpty())
        assertFalse(chatsAnteriores.any { it.id == idChat })
        assertEquals(chatsAnteriores, estado.chats.drop(1))
        assertEquals("", estado.busqueda)
        assertEquals("", estado.borrador)
    }

    @Test
    fun abrirDeNuevoElConductorNoDuplicaLaConversacion() {
        val viewModel = ChatsViewModel()
        val idChat = viewModel.abrirChatConductor("Alexandra Ramos")
        val cantidad = viewModel.uiState.value.chats.size

        assertEquals(idChat, viewModel.abrirChatConductor("Alexandra Ramos"))
        assertEquals(cantidad, viewModel.uiState.value.chats.size)
    }

    @Test
    fun conductorConChatExistenteConservaSusMensajes() {
        val viewModel = ChatsViewModel()
        val chatAnterior = viewModel.uiState.value.chats.first { it.nombreUsuario == "Laura Martínez" }

        assertEquals(chatAnterior.id, viewModel.abrirChatConductor("Laura Martínez"))
        assertEquals(chatAnterior, viewModel.uiState.value.chats.first())
        assertEquals(1, viewModel.uiState.value.chats.count { it.id == chatAnterior.id })
    }
}
