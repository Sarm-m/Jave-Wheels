package com.javeriana.javewheels.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R

/** Compatibilidad temporal: las llamadas antiguas también usan Mapbox, nunca geometría ilustrativa. */
@Deprecated("Utiliza MapaJaveWheels para configurar coordenadas reales.")
@Composable
fun MapaIlustrativo(
    modifier: Modifier = Modifier,
    onCentrarUbicacion: () -> Unit = {},
    altura: Dp? = 300.dp,
    modoEnVivo: Boolean = false,
    mostrarRutaPredeterminada: Boolean = true,
    mostrarMarcadorJaveriana: Boolean = true,
    textoUbicacion: String = stringResource(R.string.ui_tu_ubicacion),
    mostrarCentrar: Boolean = true,
    textoDestino: String = stringResource(R.string.ui_javeriana)
) {
    // Los antiguos parámetros visuales no se convierten en rutas, posiciones o ubicaciones del usuario.
    MapaJaveWheels(modifier = modifier, altura = altura, mostrarCentrar = mostrarCentrar,
        alCentrar = { onCentrarUbicacion() })
}
