package com.javeriana.javewheels.ui

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.screens.bienvenida.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.*
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import com.javeriana.javewheels.entities.*
import com.javeriana.javewheels.ui.components.*
import com.javeriana.javewheels.ui.screens.acceso.*
import com.javeriana.javewheels.ui.screens.conductor.*
import com.javeriana.javewheels.ui.screens.principal.*
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.*
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File

@RunWith(Parameterized::class)
class DisenoAdaptableTest(private val ancho: Int, private val escala: Float) {
    @get:Rule val regla = createComposeRule()

    companion object {
        @JvmStatic @Parameterized.Parameters(name = "{0}dp_letra_{1}")
        fun tamanos(): List<Array<Any>> = listOf(320, 360, 393, 411).flatMap { ancho ->
            listOf(1f, 1.3f, 1.5f).map { escala -> arrayOf<Any>(ancho, escala) }
        }
    }

    private fun capturar(nombre: String) {
        val contexto = InstrumentationRegistry.getInstrumentation().targetContext
        val carpeta = File(contexto.getExternalFilesDir(null), "capturas-ui").apply { mkdirs() }
        File(carpeta, "${nombre}_${ancho}_${escala}.png").outputStream().use {
            regla.onRoot().captureToImage().asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    @Test fun formulario_con_errores_y_placa_sin_desbordamiento() {
        val vehiculo = VehiculoViewModel()
        var guardado = false
        regla.setContent {
            CompositionLocalProvider(LocalDensity provides Density(LocalDensity.current.density, escala)) {
                JaveWheelsTheme {
                    Surface(Modifier.width(ancho.dp).fillMaxHeight()) {
                        RegistrarVehiculoScreen({}, { guardado = true }, viewModel = vehiculo)
                    }
                }
            }
        }
        regla.onNodeWithText("Guardar vehículo").performScrollTo().performClick()
        regla.onNodeWithText("Completa todos los campos para continuar.").performScrollTo().assertIsDisplayed()
        capturar("vehiculo_errores")
        val campos = listOf("Placa" to "abc123", "Marca" to "Renault", "Modelo" to "Sandero",
            "Año" to "2022", "Color" to "Gris", "Cupos disponibles" to "3")
        campos.forEach { (etiqueta, valor) ->
            regla.onNode(hasSetTextAction() and hasText(etiqueta + "\u00A0*")).performScrollTo().performTextInput(valor)
        }
        regla.onNodeWithText("ABC 123").performScrollTo().assertIsDisplayed()
        capturar("vehiculo_placa")
        regla.onNodeWithText("ABC 123").performSemanticsAction(SemanticsActions.GetTextLayoutResult) { obtener ->
            val resultados = mutableListOf<TextLayoutResult>()
            obtener(resultados)
            assertTrue("La placa debe caber en una línea: ${resultados.map { "${it.lineCount}, ${it.didOverflowWidth}, ${it.didOverflowHeight}, ${it.size}" }}", resultados.all { !it.hasVisualOverflow && it.lineCount == 1 })
        }
        regla.onNodeWithText("Guardar vehículo").performScrollTo().performClick()
        regla.runOnIdle { assertTrue(guardado) }
    }

    @Test fun pantallas_de_ambos_roles_con_letra_ampliada() {
        var pantalla by mutableIntStateOf(0)
        val inicio = InicioViewModel()
        val conductor = InicioViewModel().apply { activarPerfilConductor(Rol.CONDUCTOR) }
        val chats = ChatsViewModel()
        val recuperar = RecuperarContrasenaViewModel()
        val perfil = PerfilViewModel().apply { iniciarEdicion() }
        val vehiculoEdicion = VehiculoViewModel().apply {
            actualizarPlaca("XYZ987"); actualizarMarca("Chevrolet"); actualizarModelo("Spark")
        }
        regla.setContent {
            CompositionLocalProvider(LocalDensity provides Density(LocalDensity.current.density, escala)) {
                JaveWheelsTheme {
                    Surface(Modifier.width(ancho.dp).fillMaxHeight()) {
                        when (pantalla) {
                            0 -> LoginScreen({}, {}, {})
                            1 -> RegistroScreen({}, {})
                            2 -> InicioScreen({}, viewModel = inicio)
                            3 -> MisViajesScreen(viewModel = inicio)
                            4 -> MisViajesScreen(viewModel = conductor)
                            5 -> DetalleWheelScreen(wheelsDisponibles.last(), {}, {})
                            6 -> EditarPerfilScreen(perfil, {})
                            7 -> InformacionUsuarioScreen(inicio, perfil, {}, {}, {}, {}, VehiculoUiState())
                            8 -> EditarVehiculoScreen(vehiculoEdicion, {})
                            9 -> SeguimientoViajePasajero(reservaConfirmada.wheel, true, {}, {}, {}, false, {})
                            10 -> Scaffold(bottomBar = { BarraNavegacion(Pestana.Inicio, {}) }) { margen ->
                                InicioScreen({}, modifier = Modifier.padding(margen), viewModel = inicio)
                            }
                            11 -> ViajeEnCursoScreen(mostrarBarraNavegacion = false)
                            12 -> PublicarWheelScreen(mostrarBarraNavegacion = false)
                            13 -> AdministrarWheelScreen(mostrarBarraNavegacion = false, viewModel = conductor)
                            14 -> DetalleReservaScreen({}, {})
                            15 -> DetalleViajeConductorScreen(proximoViajeConductor, {})
                            16 -> BuscarMomentoScreen(true, inicio, {}, { _, _, _ -> })
                            17 -> RecuperarContrasenaScreen({}, {}, viewModel = recuperar)
                            18 -> VerificarCodigoScreen({}, {}, viewModel = recuperar)
                            19 -> RestablecerContrasenaScreen({}, {}, {}, viewModel = recuperar)
                            20 -> QuieresConducirScreen(true, {}, {}, {})
                            21 -> ConfirmacionScreen({})
                            22 -> ConductorCompletadoScreen({}, {})
                            23 -> CargaScreen({})
                            24 -> PerfilActualizadoScreen({})
                            25 -> VehiculoActualizadoScreen({})
                            26 -> WheelsDisponiblesScreen({}, {})
                            27 -> RutasGuardadasScreen({}, {})
                            28 -> ListaChatsScreen(chats, {})
                            29 -> DetalleChatScreen(chats, 1)
                            30 -> PerfilScreen({}, {}, viewModel = inicio)
                            31 -> BienvenidaScreen({})
                        }
                    }
                }
            }
        }
        val nombres = listOf("login", "registro", "inicio", "reservas", "historial_conductor",
            "detalle_wheel", "editar_perfil", "perfil", "editar_vehiculo", "seguimiento", "barra_inferior",
            "viaje_conductor", "publicar", "administrar", "reserva", "detalle_conductor", "programar",
            "recuperar", "verificar_codigo", "restablecer", "quieres_conducir", "confirmacion",
            "conductor_listo", "onboarding", "perfil_actualizado", "vehiculo_actualizado", "resultados", "rutas",
            "chats", "detalle_chat", "perfil_resumen", "bienvenida")
        nombres.forEachIndexed { indice, nombre ->
            regla.runOnIdle { pantalla = indice }
            regla.waitForIdle()
            capturar(nombre)
            if (indice == 31) {
                regla.onNodeWithText("JaveWheels", useUnmergedTree = true)
                    .performSemanticsAction(SemanticsActions.GetTextLayoutResult) { obtener ->
                        val resultados = mutableListOf<TextLayoutResult>()
                        obtener(resultados)
                        assertTrue("El nombre de la aplicación no debe partirse", resultados.all { it.lineCount == 1 })
                    }
            }
            if (indice == 4 || indice == 10) {
                val etiquetas = if (indice == 4) listOf("Programados", "Finalizados") else listOf("Mensajes", "Perfil")
                etiquetas.forEach { etiqueta ->
                    regla.onNodeWithText(etiqueta).performSemanticsAction(SemanticsActions.GetTextLayoutResult) { obtener ->
                        val resultados = mutableListOf<TextLayoutResult>()
                        obtener(resultados)
                        assertTrue("La etiqueta no debe partirse: $etiqueta ${resultados.map { "${it.lineCount}, ${it.didOverflowWidth}, ${it.didOverflowHeight}, ${it.getLineEnd(0)}, ${it.size}" }}", resultados.all {
                            !it.hasVisualOverflow && it.getLineEnd(0) == etiqueta.length
                        })
                    }
                }
            }
        }
        regla.onNodeWithText("Toca la pantalla para continuar", useUnmergedTree = true).performScrollTo().assertIsDisplayed()
    }
}
