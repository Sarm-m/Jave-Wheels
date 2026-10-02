package com.javeriana.javewheels.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.javeriana.javewheels.entities.Rol
import com.javeriana.javewheels.entities.usuarioDePrueba
import com.javeriana.javewheels.ui.components.BarraNavegacion
import com.javeriana.javewheels.ui.screens.acceso.ConfirmacionScreen
import com.javeriana.javewheels.ui.screens.acceso.LoginScreen
import com.javeriana.javewheels.ui.screens.acceso.RecuperarContrasenaScreen
import com.javeriana.javewheels.ui.screens.acceso.RegistroScreen
import com.javeriana.javewheels.ui.screens.acceso.RestablecerContrasenaScreen
import com.javeriana.javewheels.ui.screens.acceso.VerificarCodigoScreen
import com.javeriana.javewheels.ui.screens.bienvenida.CargaScreen
import com.javeriana.javewheels.ui.screens.bienvenida.BienvenidaScreen
import com.javeriana.javewheels.ui.screens.conductor.ConductorCompletadoScreen
import com.javeriana.javewheels.ui.screens.conductor.EditarVehiculoScreen
import com.javeriana.javewheels.ui.screens.conductor.QuieresConducirScreen
import com.javeriana.javewheels.ui.screens.conductor.RegistrarVehiculoScreen
import com.javeriana.javewheels.ui.screens.principal.EditarPerfilScreen
import com.javeriana.javewheels.ui.screens.principal.InformacionUsuarioScreen
import com.javeriana.javewheels.ui.screens.principal.InicioScreen
import com.javeriana.javewheels.ui.screens.principal.DetalleChatScreen
import com.javeriana.javewheels.ui.screens.principal.ListaChatsScreen
import com.javeriana.javewheels.ui.screens.principal.MisViajesScreen
import com.javeriana.javewheels.ui.screens.principal.PerfilActualizadoScreen
import com.javeriana.javewheels.ui.screens.principal.VehiculoActualizadoScreen
import com.javeriana.javewheels.ui.theme.JWAzul
import com.javeriana.javewheels.viewmodels.ChatsViewModel
import com.javeriana.javewheels.viewmodels.InicioViewModel
import com.javeriana.javewheels.viewmodels.PerfilViewModel
import com.javeriana.javewheels.viewmodels.VehiculoViewModel

// ============================================================
// NAVHOST — Muestra la pantalla actual
// ============================================================

@Composable
fun JWNavHost(modifier: Modifier = Modifier) {
    // La app arranca en la pantalla de Bienvenida
    val backStack = rememberNavBackStack(Rutas.Bienvenida)

    // ViewModel compartido por Inicio, Perfil y el registro de conductor
    // (así todos saben si el usuario es pasajero o conductor).
    val inicioViewModel: InicioViewModel = viewModel()

    // ViewModels de Perfil y de Editar vehículo (se limpian al cerrar sesión)
    val perfilViewModel: PerfilViewModel = viewModel()
    val vehiculoViewModel: VehiculoViewModel = viewModel()

    // ViewModel de los chats (lista y detalle comparten los mismos datos)
    val chatsViewModel: ChatsViewModel = viewModel()

    // ---------- Funciones de ayuda para navegar ----------

    fun navegarA(ruta: NavKey) {
        backStack.add(ruta)
    }

    fun volver() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    fun empezarDesde(ruta: NavKey) {
        backStack.clear()
        backStack.add(ruta)
    }

    fun irAPestana(pestana: Pestana) {
        empezarDesde(Rutas.Inicio)
        if (pestana != Pestana.Inicio) navegarA(pestana.ruta)
    }


    val rutaActual = backStack.lastOrNull()
    val pestanaActual = when (rutaActual) {
        Rutas.EditarPerfil, Rutas.EditarVehiculo -> Pestana.Perfil
        is Rutas.DetalleChat -> Pestana.Mensajes
        else -> Pestana.entries.find { it.ruta == rutaActual }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = if (rutaActual == Rutas.Bienvenida) JWAzul else MaterialTheme.colorScheme.background,
        bottomBar = {
            if (pestanaActual != null) {
                BarraNavegacion(
                    pestanaActual = pestanaActual,
                    onSeleccionar = { irAPestana(it) }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { volver() },
            // consumeWindowInsets evita un hueco de más cuando sale el teclado en el chat
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            entryProvider = entryProvider {

                // ==================== BIENVENIDA ====================
                entry<Rutas.Bienvenida> {
                    BienvenidaScreen(onTerminar = { empezarDesde(Rutas.Carga) })
                }
                entry<Rutas.Carga> {
                    CargaScreen(onComenzar = { empezarDesde(Rutas.IniciarSesion) })
                }

                // ==================== ACCESO ====================
                entry<Rutas.IniciarSesion> {
                    LoginScreen(
                        onIniciarSesion = { empezarDesde(Rutas.Inicio) },
                        onOlvidoContrasena = { navegarA(Rutas.RecuperarContrasena) },
                        onCrearCuenta = { navegarA(Rutas.Registrarse) }
                    )
                }
                entry<Rutas.Registrarse> {
                    RegistroScreen(
                        onVolver = { volver() },
                        onCuentaCreada = { empezarDesde(Rutas.QuieresConducir(desdeRegistro = true)) }
                    )
                }
                entry<Rutas.RecuperarContrasena> {
                    RecuperarContrasenaScreen(
                        onVolver = { volver() },
                        onCodigoEnviado = { navegarA(Rutas.VerificarCodigo) }
                    )
                }
                entry<Rutas.VerificarCodigo> {
                    VerificarCodigoScreen(
                        onVolver = { volver() },
                        onCodigoVerificado = { navegarA(Rutas.RestablecerContrasena) }
                    )
                }
                entry<Rutas.RestablecerContrasena> {
                    RestablecerContrasenaScreen(
                        onVolver = { volver() },
                        onContrasenaRestablecida = { empezarDesde(Rutas.Confirmacion) },
                        onIrALogin = { empezarDesde(Rutas.IniciarSesion) }
                    )
                }
                entry<Rutas.Confirmacion> {
                    ConfirmacionScreen(onIrALogin = { empezarDesde(Rutas.IniciarSesion) })
                }

                // ==================== REGISTRO DE CONDUCTOR ====================
                entry<Rutas.QuieresConducir> { ruta ->
                    QuieresConducirScreen(
                        desdeRegistro = ruta.desdeRegistro,
                        onVolver = { volver() },
                        onRegistrarVehiculo = { navegarA(Rutas.RegistrarVehiculo) },
                        onSerPasajero = {
                            inicioViewModel.usarComoPasajero()
                            empezarDesde(Rutas.Inicio)
                        }
                    )
                }
                entry<Rutas.RegistrarVehiculo> {
                    RegistrarVehiculoScreen(
                        onVolver = { volver() },
                        onGuardado = { empezarDesde(Rutas.ConductorCompletado) }
                    )
                }
                entry<Rutas.ConductorCompletado> {
                    ConductorCompletadoScreen(
                        onIrInicioConductor = {
                            inicioViewModel.activarPerfilConductor(Rol.CONDUCTOR)
                            empezarDesde(Rutas.Inicio)
                        },
                        onContinuarPasajero = {
                            inicioViewModel.activarPerfilConductor(Rol.PASAJERO)
                            empezarDesde(Rutas.Inicio)
                        }
                    )
                }

                // ==================== PESTAÑAS ====================
                entry<Rutas.Inicio> {
                    InicioScreen(
                        viewModel = inicioViewModel,
                        onQuiereSerConductor = { navegarA(Rutas.QuieresConducir(desdeRegistro = false)) }
                    )
                }
                entry<Rutas.MisViajes> {
                    MisViajesScreen()
                }
                entry<Rutas.Mensajes> {
                    ListaChatsScreen(
                        viewModel = chatsViewModel,
                        onAbrirChat = { idChat ->
                            chatsViewModel.actualizarBorrador("")
                            navegarA(Rutas.DetalleChat(idChat))
                        }
                    )
                }

                entry<Rutas.DetalleChat> { ruta ->
                    DetalleChatScreen(
                        viewModel = chatsViewModel,
                        idChat = ruta.idChat
                    )
                }
                entry<Rutas.Perfil> {
                    InformacionUsuarioScreen(
                        inicioViewModel = inicioViewModel,
                        perfilViewModel = perfilViewModel,
                        onEditarPerfil = {
                            perfilViewModel.iniciarEdicion()
                            navegarA(Rutas.EditarPerfil)
                        },
                        onEditarVehiculo = {
                            vehiculoViewModel.cargarParaEdicion(usuarioDePrueba)
                            navegarA(Rutas.EditarVehiculo)
                        },
                        onRegistrarVehiculo = { navegarA(Rutas.RegistrarVehiculo) },
                        onCerrarSesion = {
                            perfilViewModel.reiniciar()
                            vehiculoViewModel.reiniciar()
                            chatsViewModel.reiniciar()
                            empezarDesde(Rutas.IniciarSesion)
                        }
                    )
                }

                // ==================== EDICIÓN DE PERFIL Y VEHÍCULO ====================
                entry<Rutas.EditarPerfil> {
                    EditarPerfilScreen(
                        viewModel = perfilViewModel,
                        onGuardado = { empezarDesde(Rutas.PerfilActualizado) }
                    )
                }
                entry<Rutas.EditarVehiculo> {
                    EditarVehiculoScreen(
                        viewModel = vehiculoViewModel,
                        onGuardado = { empezarDesde(Rutas.VehiculoActualizado) }
                    )
                }
                entry<Rutas.PerfilActualizado> {
                    PerfilActualizadoScreen(onIrAlMenu = { empezarDesde(Rutas.Inicio) })
                }
                entry<Rutas.VehiculoActualizado> {
                    VehiculoActualizadoScreen(onIrAlMenu = { empezarDesde(Rutas.Inicio) })
                }
            }
        )
    }
}