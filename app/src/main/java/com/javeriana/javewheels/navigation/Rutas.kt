package com.javeriana.javewheels.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// ============================================================
// RUTAS (NavKey) — A qué pantalla va
// ============================================================

@Serializable
sealed class Rutas : NavKey {

    // --- Bienvenida ---
    @Serializable data object Bienvenida : Rutas()
    @Serializable data object Carga : Rutas()

    // --- Acceso ---
    @Serializable data object IniciarSesion : Rutas()
    @Serializable data object Registrarse : Rutas()
    @Serializable data object RecuperarContrasena : Rutas()
    @Serializable data object VerificarCodigo : Rutas()
    @Serializable data object RestablecerContrasena : Rutas()
    @Serializable data object Confirmacion : Rutas()

    // --- Registro de conductor ---
    // Ruta CON datos (como PokemonDetailRoute en la Clase 4):
    // desdeRegistro = true si llega justo después de crear la cuenta.
    @Serializable data class QuieresConducir(val desdeRegistro: Boolean) : Rutas()
    @Serializable data object RegistrarVehiculo : Rutas()
    @Serializable data object ConductorCompletado : Rutas()

    @Serializable data class DetalleChat(val idChat: Int) : Rutas()
    @Serializable data object EditarPerfil : Rutas()
    @Serializable data object EditarVehiculo : Rutas()
    @Serializable data object PerfilActualizado : Rutas()
    @Serializable data object VehiculoActualizado : Rutas()

    // --- Pestañas de la barra de navegación ---
    @Serializable data object Inicio : Rutas()
    @Serializable data object MisViajes : Rutas()
    @Serializable data object Mensajes : Rutas()
    @Serializable data object Perfil : Rutas()
    //  pantallas 10,11...
    @Serializable data object VerViajeEnVivo : Rutas()
    @Serializable data object PublicarWheel : Rutas()
    @Serializable data class AdministrarWheel(val viajeId: String) : Rutas()
    @Serializable data object ViajeEnCurso : Rutas()

    @Serializable data object VerViajeBuseta : Rutas()

    // --- Búsqueda, reservas e historial ---
    @Serializable data class WheelsDisponibles(val origen: String = "Mi ubicación", val destino: String = "", val desdeMillis: Long? = null, val programado: Boolean = false) : Rutas()
    @Serializable data class DetalleWheel(val wheelId: String) : Rutas()
    @Serializable data object DetalleReserva : Rutas()
    @Serializable data class ResumenWheel(val wheelId: String) : Rutas()
    @Serializable data class DetalleViajeConductor(val viajeId: String) : Rutas()
    @Serializable data object RutasGuardadas : Rutas()
    @Serializable data object BuscarAhora : Rutas()
    @Serializable data object ProgramarBusqueda : Rutas()

}

// ============================================================
// PESTAÑAS de la barra inferior (NavigationBar)
// ============================================================

enum class Pestana(
    val titulo: String,
    val icono: ImageVector,
    val iconoSeleccionado: ImageVector,
    val ruta: Rutas
) {
    Inicio("Inicio", Icons.Outlined.Home, Icons.Filled.Home, Rutas.Inicio),
    MisViajes("Mis viajes", Icons.Outlined.DateRange, Icons.Filled.DateRange, Rutas.MisViajes),
    Mensajes("Mensajes", Icons.Outlined.ChatBubbleOutline, Icons.Filled.ChatBubble, Rutas.Mensajes),
    Perfil("Perfil", Icons.Outlined.Person, Icons.Filled.Person, Rutas.Perfil)
}
