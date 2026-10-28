package com.javeriana.javewheels.entities

/** Coordenadas geográficas; los nombres de direcciones no se convierten en puntos automáticamente. */
data class CoordenadaMapa(val longitud: Double, val latitud: Double) {
    init {
        require(longitud.isFinite() && longitud in -180.0..180.0)
        require(latitud.isFinite() && latitud in -90.0..90.0)
    }
}

val POSICION_JAVERIANA = CoordenadaMapa(-74.0641630119028, 4.627389316647156)

/** Permite agregar después orígenes, destinos o recogidas que tengan coordenadas verificadas. */
data class MarcadorMapa(val id: String, val titulo: String, val posicion: CoordenadaMapa)

data class CamaraMapa(val centro: CoordenadaMapa, val zoom: Double)
