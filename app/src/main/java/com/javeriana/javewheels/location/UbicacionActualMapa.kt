package com.javeriana.javewheels.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.javeriana.javewheels.entities.CoordenadaMapa
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

sealed interface ResultadoUbicacionMapa {
    data class Disponible(val posicion: CoordenadaMapa, val aproximada: Boolean) : ResultadoUbicacionMapa
    data object PermisoDenegado : ResultadoUbicacionMapa
    data object ServicioDesactivado : ResultadoUbicacionMapa
    data object NoDisponible : ResultadoUbicacionMapa
}

/** La interfaz permite verificar los estados sin simular que existen posiciones GPS reales. */
interface FuenteUbicacionMapa {
    fun tienePermiso(): Boolean
    suspend fun consultar(): ResultadoUbicacionMapa
}

/** Adapta el proveedor del ejemplo de clase a una consulta puntual, cancelable y sin grabación. */
class UbicacionActualMapa(contexto: Context) : FuenteUbicacionMapa {
    private val contexto = contexto.applicationContext

    override fun tienePermiso(): Boolean = permisoConcedido(Manifest.permission.ACCESS_COARSE_LOCATION) ||
        permisoConcedido(Manifest.permission.ACCESS_FINE_LOCATION)

    private fun permisoConcedido(permiso: String): Boolean =
        ContextCompat.checkSelfPermission(contexto, permiso) == PackageManager.PERMISSION_GRANTED

    override suspend fun consultar(): ResultadoUbicacionMapa {
        // Se vuelve a comprobar aquí porque el permiso puede revocarse mientras la pantalla está abierta.
        val fina = ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        val aproximada = ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        if (!fina && !aproximada) return ResultadoUbicacionMapa.PermisoDenegado
        val administrador = contexto.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
            ?: return ResultadoUbicacionMapa.NoDisponible
        if (!LocationManagerCompat.isLocationEnabled(administrador)) return ResultadoUbicacionMapa.ServicioDesactivado

        return try {
            withTimeoutOrNull(12_000L) {
                suspendCancellableCoroutine { continuacion ->
                    val cancelacion = CancellationTokenSource()
                    continuacion.invokeOnCancellation { cancelacion.cancel() }
                    val solicitud = CurrentLocationRequest.Builder()
                        .setPriority(if (fina) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY)
                        .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                        .setMaxUpdateAgeMillis(30_000L)
                        .setDurationMillis(10_000L)
                        .build()
                    LocationServices.getFusedLocationProviderClient(contexto)
                        .getCurrentLocation(solicitud, cancelacion.token)
                        .addOnSuccessListener { ubicacion ->
                            if (continuacion.isActive) {
                                val posicion = ubicacion?.let {
                                    runCatching { CoordenadaMapa(it.longitude, it.latitude) }.getOrNull()
                                }
                                continuacion.resume(if (posicion != null) {
                                    ResultadoUbicacionMapa.Disponible(posicion, aproximada = !fina)
                                } else ResultadoUbicacionMapa.NoDisponible)
                            }
                        }
                        .addOnFailureListener { error ->
                            if (continuacion.isActive) continuacion.resume(
                                if (error is SecurityException) ResultadoUbicacionMapa.PermisoDenegado
                                else ResultadoUbicacionMapa.NoDisponible
                            )
                        }
                        .addOnCanceledListener {
                            if (continuacion.isActive) continuacion.resume(ResultadoUbicacionMapa.NoDisponible)
                        }
                }
            } ?: ResultadoUbicacionMapa.NoDisponible
        } catch (_: SecurityException) {
            ResultadoUbicacionMapa.PermisoDenegado
        }
    }
}
