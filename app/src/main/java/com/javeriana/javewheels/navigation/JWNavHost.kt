package com.javeriana.javewheels.navigation

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
import com.javeriana.javewheels.entities.wheelsFinalizados
import com.javeriana.javewheels.entities.viajesProgramadosConductor
import com.javeriana.javewheels.entities.viajesFinalizadosConductor
import com.javeriana.javewheels.entities.wheelsDisponibles
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
import com.javeriana.javewheels.ui.screens.conductor.QuieresConducirScreen
import com.javeriana.javewheels.ui.screens.conductor.RegistrarVehiculoScreen
import com.javeriana.javewheels.ui.screens.principal.DetalleViajeConductorScreen
import com.javeriana.javewheels.ui.screens.principal.RutasGuardadasScreen
import com.javeriana.javewheels.ui.screens.principal.BuscarMomentoScreen
import com.javeriana.javewheels.ui.screens.principal.InicioScreen
import com.javeriana.javewheels.ui.screens.principal.MensajesScreen
import com.javeriana.javewheels.ui.screens.principal.MisViajesScreen
import com.javeriana.javewheels.ui.screens.principal.PerfilScreen
import com.javeriana.javewheels.ui.screens.principal.WheelsDisponiblesScreen
import com.javeriana.javewheels.ui.screens.principal.DetalleWheelScreen
import com.javeriana.javewheels.ui.screens.principal.DetalleReservaScreen
import com.javeriana.javewheels.ui.theme.JWAzul
import com.javeriana.javewheels.viewmodels.InicioViewModel

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

    // ---------- Funciones de ayuda para navegar ----------

    /** Ir a una pantalla nueva (se apila encima). */
    fun navegarA(ruta: NavKey) {
        backStack.add(ruta)
    }

    /** Volver a la pantalla anterior (si hay alguna). */
    fun volver() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    /**
     * Borra toda la pila y empieza desde [ruta].
     * Se usa cuando NO se debe poder volver atrás
     * (ej. después de iniciar sesión no se regresa al login).
     */
    fun empezarDesde(ruta: NavKey) {
        backStack.clear()
        backStack.add(ruta)
    }

    /** Cambia de pestaña: Inicio siempre queda de base para que "atrás" vuelva a Inicio. */
    fun irAPestana(pestana: Pestana) {
        empezarDesde(Rutas.Inicio)
        if (pestana != Pestana.Inicio) navegarA(pestana.ruta)
    }


    val rutaActual = backStack.lastOrNull()
    val pestanaActual = when (rutaActual) {
        is Rutas.WheelsDisponibles, is Rutas.DetalleWheel, Rutas.RutasGuardadas, Rutas.BuscarAhora, Rutas.ProgramarBusqueda -> Pestana.Inicio
        Rutas.DetalleReserva, is Rutas.ResumenWheel, is Rutas.DetalleViajeConductor -> Pestana.MisViajes
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
            modifier = Modifier.padding(innerPadding),
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
                // La ruta indica si se acaba de crear la cuenta.
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
                        onQuiereSerConductor = { navegarA(Rutas.QuieresConducir(desdeRegistro = false)) },
                        onBuscarWheels = {
                            navegarA(if (inicioViewModel.uiState.value.viajeProgramado) Rutas.ProgramarBusqueda else Rutas.BuscarAhora)
                        },
                        onGuardados = { navegarA(Rutas.RutasGuardadas) },
                        onSeleccionarMomento = { navegarA(if (it) Rutas.ProgramarBusqueda else Rutas.BuscarAhora) },
                        onVerViajeConductor = { navegarA(Rutas.DetalleViajeConductor(inicioViewModel.uiState.value.proximoViaje.id)) }
                    )
                }
                entry<Rutas.BuscarAhora> {
                    BuscarMomentoScreen(programado = false, viewModel = inicioViewModel, onVolver = { volver() },
                        onBuscar = { origen, destino, salida -> navegarA(Rutas.WheelsDisponibles(origen, destino, salida, false)) })
                }
                entry<Rutas.ProgramarBusqueda> {
                    BuscarMomentoScreen(programado = true, viewModel = inicioViewModel, onVolver = { volver() },
                        onBuscar = { origen, destino, salida -> navegarA(Rutas.WheelsDisponibles(origen, destino, salida, true)) })
                }
                entry<Rutas.MisViajes> {
                    MisViajesScreen(
                        viewModel = inicioViewModel,
                        onVerReserva = { navegarA(Rutas.DetalleReserva) },
                        onVerResumen = { navegarA(Rutas.ResumenWheel(it)) },
                        onVerViajeConductor = { navegarA(Rutas.DetalleViajeConductor(it)) },
                        onQuiereSerConductor = { navegarA(Rutas.QuieresConducir(desdeRegistro = false)) }
                    )
                }
                entry<Rutas.WheelsDisponibles> { ruta -> // Si la ruta actual es WheelsDisponibles, dibuja esa pantalla; cuando el usuario elija un Wheel, abre su detalle
                    WheelsDisponiblesScreen(
                        origen = ruta.origen, destino = ruta.destino,
                        desdeMillis = ruta.desdeMillis, programado = ruta.programado,
                        onVolver = { volver() },
                        onVerWheel = { navegarA(Rutas.DetalleWheel(it)) }
                    )
                }
                entry<Rutas.DetalleWheel> { ruta ->
                    val wheel = wheelsDisponibles.firstOrNull { it.id == ruta.wheelId }
                    if (wheel != null) {
                        DetalleWheelScreen(
                            wheel = wheel,
                            onVolver = { volver() },
                            onSolicitarCupo = { irAPestana(Pestana.MisViajes) }
                        )
                    }
                }
                entry<Rutas.ResumenWheel> { ruta ->
                    val wheel = wheelsFinalizados.firstOrNull { it.wheel.id == ruta.wheelId }?.wheel
                    if (wheel != null) DetalleWheelScreen(wheel, onVolver = { volver() }, onSolicitarCupo = {}, finalizado = true)
                }
                entry<Rutas.DetalleViajeConductor> { ruta ->
                    val viaje = (viajesProgramadosConductor + viajesFinalizadosConductor).firstOrNull { it.id == ruta.viajeId }
                    if (viaje != null) DetalleViajeConductorScreen(viaje, onVolver = { volver() })
                }
                entry<Rutas.RutasGuardadas> {
                    RutasGuardadasScreen(onVolver = { volver() }, onElegirRuta = {
                        inicioViewModel.seleccionarRutaGuardada(it)
                        volver()
                    })
                }
                entry<Rutas.DetalleReserva> {
                    DetalleReservaScreen(onVolver = { volver() })
                }
                entry<Rutas.Mensajes> {
                    MensajesScreen()
                }
                entry<Rutas.Perfil> {
                    PerfilScreen(
                        viewModel = inicioViewModel,
                        onRegistrarVehiculo = { navegarA(Rutas.RegistrarVehiculo) },
                        onCerrarSesion = { empezarDesde(Rutas.IniciarSesion) }
                    )
                }
            }
        )
    }
}
