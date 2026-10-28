package com.javeriana.javewheels.ui

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import com.javeriana.javewheels.ui.components.AvatarUsuario
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.File

class FotografiaPerfilTest {
    @get:Rule val regla = createComposeRule()

    @Test fun imagen_no_seleccionada_y_archivo_inaccesible() {
        var foto by mutableStateOf<Uri?>(null)
        regla.setContent {
            JaveWheelsTheme { AvatarUsuario(foto = foto, descripcion = "Foto de perfil", modifier = Modifier.size(120.dp)) }
        }
        regla.onNodeWithContentDescription("Foto de perfil").assertIsDisplayed()
        regla.runOnIdle { foto = Uri.parse("content://com.javeriana.inexistente/foto") }
        regla.waitUntil(5_000) {
            regla.onAllNodesWithContentDescription("No se pudo mostrar la foto. Selecciona otra imagen.")
                .fetchSemanticsNodes().isNotEmpty()
        }
        regla.onNodeWithContentDescription("No se pudo mostrar la foto. Selecciona otra imagen.").assertIsDisplayed()
    }

    @Test fun fotografia_panoramica_se_carga_en_contenedor_cuadrado() {
        val contexto = InstrumentationRegistry.getInstrumentation().targetContext
        val archivo = File(contexto.cacheDir, "foto-panoramica-prueba.png")
        Bitmap.createBitmap(2000, 200, Bitmap.Config.ARGB_8888).apply {
            eraseColor(android.graphics.Color.BLUE)
            archivo.outputStream().use { compress(Bitmap.CompressFormat.PNG, 100, it) }
            recycle()
        }
        var disponible: Boolean? = null
        regla.setContent {
            JaveWheelsTheme { AvatarUsuario(foto = Uri.fromFile(archivo), descripcion = "Foto panorámica",
                modifier = Modifier.size(120.dp), alCargar = { disponible = it }) }
        }
        regla.waitUntil(5_000) { disponible == true }
        regla.onNodeWithContentDescription("Foto panorámica").assertIsDisplayed()
        val limites = regla.onNodeWithContentDescription("Foto panorámica").fetchSemanticsNode().boundsInRoot
        assertTrue("La imagen respeta el contenedor", limites.width == limites.height)
        archivo.delete()
    }
}
