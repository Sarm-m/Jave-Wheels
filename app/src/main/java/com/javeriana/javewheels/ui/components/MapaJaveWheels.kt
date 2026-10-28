package com.javeriana.javewheels.ui.components

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.CamaraMapa
import com.javeriana.javewheels.entities.CoordenadaMapa
import com.javeriana.javewheels.entities.MarcadorMapa
import com.javeriana.javewheels.entities.POSICION_JAVERIANA
import com.javeriana.javewheels.location.FuenteUbicacionMapa
import com.javeriana.javewheels.location.ResultadoCentradoMapa
import com.javeriana.javewheels.location.ResultadoUbicacionMapa
import com.javeriana.javewheels.location.UbicacionActualMapa
import com.javeriana.javewheels.location.resolverCentradoMapa
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.BooleanValue
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.standard.rememberStandardStyleState
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Composable
fun recordarFuenteUbicacionMapa(): FuenteUbicacionMapa {
    val contexto = LocalContext.current.applicationContext
    return remember(contexto) { UbicacionActualMapa(contexto) }
}

/** La cámara del ejemplo de clase se conserva mediante rememberSaveable del SDK. */
@Composable
fun recordarCamaraMapa(
    posicionInicial: CoordenadaMapa = POSICION_JAVERIANA,
    zoomInicial: Double = 15.5
): MapViewportState = rememberMapViewportState {
    setCameraOptions {
        center(Point.fromLngLat(posicionInicial.longitud, posicionInicial.latitud))
        zoom(zoomInicial)
        pitch(0.0)
        bearing(0.0)
    }
}

/** Superficie geográfica reutilizable. No geocodifica direcciones, calcula rutas ni sigue vehículos. */
@OptIn(MapboxExperimental::class, com.mapbox.maps.MapboxExperimental::class)
@Composable
fun MapaJaveWheels(
    modifier: Modifier = Modifier,
    altura: Dp? = 300.dp,
    posicionInicial: CoordenadaMapa = POSICION_JAVERIANA,
    zoomInicial: Double = 15.5,
    marcadores: List<MarcadorMapa> = emptyList(),
    mostrarCentrar: Boolean = true,
    margenInferiorControles: Dp = 0.dp,
    estadoCamara: MapViewportState? = null,
    alCambiarCamara: (CamaraMapa) -> Unit = {},
    alCentrar: (ResultadoCentradoMapa) -> Unit = {},
    fuenteUbicacion: FuenteUbicacionMapa = recordarFuenteUbicacionMapa()
) {
    val contenedor = if (altura != null) modifier.fillMaxWidth().height(altura) else modifier
    val token = stringResource(R.string.mapbox_access_token)
    if (LocalInspectionMode.current || token.isBlank()) {
        Box(contenedor.background(MaterialTheme.colorScheme.surfaceContainerLow)) {
            Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.mapa_configuracion_pendiente), style = MaterialTheme.typography.titleSmall)
                Text(stringResource(R.string.mapa_configurar_token), style = MaterialTheme.typography.bodySmall)
                Text(stringResource(R.string.mapa_referencia_inicial), style = MaterialTheme.typography.bodySmall)
            }
        }
        return
    }

    val camara = estadoCamara ?: recordarCamaraMapa(posicionInicial, zoomInicial)
    val mapa = rememberMapState()
    val estilo = rememberStandardStyleState {
        configurationsState.apply {
            lightPreset = LightPresetValue.DAY
            showRoadLabels = BooleanValue(true)
            show3dObjects = BooleanValue(true)
        }
    }
    val contexto = LocalContext.current
    val descripcionMapa = stringResource(R.string.mapa_interactivo)
    val descripcionBusqueda = stringResource(R.string.mapa_buscando_ubicacion)
    val actividad = LocalActivity.current
    val alcance = rememberCoroutineScope()
    var consulta by remember { mutableStateOf<Job?>(null) }
    var cargandoMapa by remember { mutableStateOf(true) }
    var errorMapa by remember { mutableStateOf(false) }
    var avisoUbicacion by remember { mutableStateOf<ResultadoUbicacionMapa?>(null) }
    var posicionConsultada by remember { mutableStateOf<CoordenadaMapa?>(null) }
    var mostrarPermiso by remember { mutableStateOf(false) }
    var permisoSolicitado by rememberSaveable { mutableStateOf(false) }
    var buscandoUbicacion by remember { mutableStateOf(false) }
    val cambioCamaraActual by rememberUpdatedState(alCambiarCamara)
    val centradoActual by rememberUpdatedState(alCentrar)

    fun aplicarCentrado(resultado: ResultadoCentradoMapa) {
        camara.setCameraOptions {
            center(Point.fromLngLat(resultado.posicion.longitud, resultado.posicion.latitud))
            zoom(zoomInicial)
        }
        avisoUbicacion = resultado.ubicacion
        posicionConsultada = (resultado.ubicacion as? ResultadoUbicacionMapa.Disponible)?.posicion
        centradoActual(resultado)
    }

    fun consultarUbicacion() {
        if (buscandoUbicacion) return
        consulta = alcance.launch {
            buscandoUbicacion = true
            try {
                aplicarCentrado(resolverCentradoMapa(fuenteUbicacion, posicionInicial))
            } finally {
                buscandoUbicacion = false
            }
        }
    }

    val permisos = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        permisoSolicitado = true
        if (fuenteUbicacion.tienePermiso()) consultarUbicacion()
        else aplicarCentrado(ResultadoCentradoMapa(posicionInicial, ResultadoUbicacionMapa.PermisoDenegado))
    }
    // Ni el SDK ni este componente activan seguimiento continuo: solo se observa una consulta puntual.
    LifecycleEventEffect(Lifecycle.Event.ON_STOP) { consulta?.cancel() }
    LaunchedEffect(mapa) {
        mapa.mapLoadedEvents.collect { cargandoMapa = false; errorMapa = false }
    }
    LaunchedEffect(mapa) {
        mapa.mapLoadingErrorEvents.collect { errorMapa = true; cargandoMapa = false }
    }
    LaunchedEffect(camara) {
        snapshotFlow { camara.cameraState }.filterNotNull().collect {
            cambioCamaraActual(CamaraMapa(CoordenadaMapa(it.center.longitude(), it.center.latitude()), it.zoom))
        }
    }

    Box(contenedor) {
        MapboxMap(
            modifier = Modifier.fillMaxSize().semantics { contentDescription = descripcionMapa },
            mapViewportState = camara,
            mapState = mapa,
            style = { MapboxStandardStyle(standardStyleState = estilo) },
            // Se mantienen ambos ornamentos del SDK, incluidos sus diálogos legales y de telemetría.
            logo = { Logo(Modifier.padding(bottom = margenInferiorControles)) },
            attribution = { Attribution(Modifier.padding(bottom = margenInferiorControles)) },
            scaleBar = {},
            compass = { Compass(Modifier.padding(end = 60.dp)) }
        ) {
            marcadores.forEach { marcador ->
                key(marcador.id) {
                    MarcadorGeografico(marcador.posicion, marcador.titulo)
                }
            }
            posicionConsultada?.let {
                MarcadorGeografico(it, stringResource(R.string.mapa_ubicacion_consultada))
            }
        }
        Column(Modifier.align(Alignment.TopStart).padding(8.dp).fillMaxWidth(0.75f),
            verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Surface(shape = MaterialTheme.shapes.small, color = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f)) {
                Text(
                    when {
                        buscandoUbicacion -> stringResource(R.string.mapa_buscando_ubicacion)
                        avisoUbicacion != null -> textoEstadoUbicacion(avisoUbicacion)
                        errorMapa -> stringResource(R.string.mapa_error_carga)
                        cargandoMapa -> stringResource(R.string.mapa_cargando)
                        else -> textoEstadoUbicacion(avisoUbicacion)
                    },
                    Modifier.padding(8.dp).semantics { liveRegion = LiveRegionMode.Polite },
                    style = MaterialTheme.typography.labelSmall
                )
            }
            if (avisoUbicacion != null && errorMapa) Surface(shape = MaterialTheme.shapes.small) {
                Text(stringResource(R.string.mapa_error_carga), Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
            }
        }
        if (mostrarCentrar) {
            BotonCentradoMapa(
                onCentrar = {
                    if (fuenteUbicacion.tienePermiso()) consultarUbicacion() else mostrarPermiso = true
                },
                habilitado = !buscandoUbicacion,
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
            )
            if (buscandoUbicacion) CircularProgressIndicator(
                Modifier.align(Alignment.TopEnd).padding(top = 64.dp, end = 20.dp).size(24.dp)
                    .semantics { contentDescription = descripcionBusqueda },
                strokeWidth = 2.dp
            )
        }
    }

    if (mostrarPermiso) {
        val permanente = permisoSolicitado && actividad != null &&
            !ActivityCompat.shouldShowRequestPermissionRationale(actividad, Manifest.permission.ACCESS_COARSE_LOCATION)
        AlertDialog(
            onDismissRequest = { mostrarPermiso = false },
            title = { Text(stringResource(R.string.mapa_permiso_titulo)) },
            text = { Text(stringResource(R.string.mapa_permiso_explicacion)) },
            confirmButton = {
                TextButton(onClick = {
                    mostrarPermiso = false
                    if (permanente) {
                        contexto.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", contexto.packageName, null)))
                    } else permisos.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
                }) { Text(stringResource(if (permanente) R.string.mapa_abrir_ajustes else R.string.mapa_permitir_ubicacion)) }
            },
            dismissButton = {
                TextButton(onClick = {
                    mostrarPermiso = false
                    aplicarCentrado(ResultadoCentradoMapa(posicionInicial, ResultadoUbicacionMapa.PermisoDenegado))
                }) { Text(stringResource(R.string.mapa_usar_referencia)) }
            }
        )
    }
}

/** Anotación anclada por la punta, con tamaño apropiado para los mapas compactos. */
@Composable
@MapboxMapComposable
private fun MarcadorGeografico(posicion: CoordenadaMapa, titulo: String) {
    val imagen = rememberIconImage(R.drawable.marcador_mapa)
    val colorTexto = MaterialTheme.colorScheme.primary
    PointAnnotation(Point.fromLngLat(posicion.longitud, posicion.latitud)) {
        iconImage = imagen
        iconAnchor = IconAnchor.BOTTOM
        textField = titulo
        textAnchor = TextAnchor.TOP
        textOffset = listOf(0.0, 0.3)
        textSize = 12.0
        textColor = colorTexto
        textHaloColor = Color.White
        textHaloWidth = 2.0
    }
}

@Composable
private fun textoEstadoUbicacion(resultado: ResultadoUbicacionMapa?): String = stringResource(when (resultado) {
    is ResultadoUbicacionMapa.Disponible -> if (resultado.aproximada) R.string.mapa_centrado_aproximado else R.string.mapa_centrado_usuario
    ResultadoUbicacionMapa.PermisoDenegado -> R.string.mapa_permiso_denegado
    ResultadoUbicacionMapa.ServicioDesactivado -> R.string.mapa_servicio_desactivado
    ResultadoUbicacionMapa.NoDisponible -> R.string.mapa_ubicacion_no_disponible
    null -> R.string.mapa_referencia_inicial
})

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun MapaJaveWheelsPreview() {
    JaveWheelsTheme { MapaJaveWheels() }
}
