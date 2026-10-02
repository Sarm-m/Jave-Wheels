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
import com.javeriana.javewheels.ui.components.BarraNavegacion
import com.javeriana.javewheels.ui.screens.pasajero.VerViajeBusetaScreen
import com.javeriana.javewheels.ui.screens.VerViajeEnVivoScreen
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
import com.javeriana.javewheels.ui.screens.principal.InicioScreen
import com.javeriana.javewheels.ui.screens.principal.MensajesScreen
import com.javeriana.javewheels.ui.screens.principal.MisViajesScreen
import com.javeriana.javewheels.ui.screens.principal.PerfilScreen
import com.javeriana.javewheels.ui.screens.conductor.PublicarWheelScreen
import com.javeriana.javewheels.ui.screens.conductor.AdministrarWheelScreen
import com.javeriana.javewheels.ui.screens.conductor.ViajeEnCursoScreen
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
    val pestanaActual = Pestana.entries.find { it.ruta == rutaActual }

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
                // "ruta" trae el dato desdeRegistro (ruta con datos, Clase 4)
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
                    MensajesScreen()
                }
                entry<Rutas.Perfil> {
                    PerfilScreen(
                        viewModel = inicioViewModel,
                        onRegistrarVehiculo = { navegarA(Rutas.RegistrarVehiculo) },
                        onCerrarSesion = { empezarDesde(Rutas.IniciarSesion) }
                    )
                }

                // ==================== VIAJEs  ====================
                entry<Rutas.VerViajeEnVivo> {
                    VerViajeEnVivoScreen(onVolver = { volver() })
                }
                entry<Rutas.VerViajeEnVivo> {
                    VerViajeBusetaScreen(onVolver = { volver() })
                }
                entry<Rutas.PublicarWheel> {
                    PublicarWheelScreen(onBack = { volver() })
                }

                entry<Rutas.AdministrarWheel> {
                    AdministrarWheelScreen(onBack = { volver() })
                }

                entry<Rutas.ViajeEnCurso> {
                    ViajeEnCursoScreen(onBack = { volver() })
                }

            }
        )
    }
}
