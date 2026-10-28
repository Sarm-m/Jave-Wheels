package com.javeriana.javewheels.location

import com.javeriana.javewheels.entities.CoordenadaMapa

data class ResultadoCentradoMapa(val posicion: CoordenadaMapa, val ubicacion: ResultadoUbicacionMapa)

/** No consulta el proveedor sin permiso; cualquier fallo vuelve a la referencia conocida. */
suspend fun resolverCentradoMapa(
    fuente: FuenteUbicacionMapa,
    posicionInicial: CoordenadaMapa
): ResultadoCentradoMapa {
    val resultado = if (fuente.tienePermiso()) fuente.consultar() else ResultadoUbicacionMapa.PermisoDenegado
    return ResultadoCentradoMapa(
        posicion = (resultado as? ResultadoUbicacionMapa.Disponible)?.posicion ?: posicionInicial,
        ubicacion = resultado
    )
}
