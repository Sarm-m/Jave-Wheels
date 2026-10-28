package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.MarcadorMapa
import com.javeriana.javewheels.entities.POSICION_JAVERIANA
import com.javeriana.javewheels.entities.Rol
import com.javeriana.javewheels.entities.fechaBusqueda
import com.javeriana.javewheels.entities.horaBusqueda
import com.javeriana.javewheels.ui.components.*
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.InicioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    onQuiereSerConductor: () -> Unit,
    onBuscarWheels: () -> Unit = {},
    onGuardados: () -> Unit = {},
    onSeleccionarMomento: (Boolean) -> Unit = {},
    onVerViajeConductor: () -> Unit = {},
    onPublicarWheel: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: InicioViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val hoja = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded, skipHiddenState = true)
    val estadoPanel = rememberBottomSheetScaffoldState(bottomSheetState = hoja)
    val scrollContenido = rememberScrollState()
    var tocandoContenido by remember { mutableStateOf(false) }
    var mantenerExpandido by remember { mutableStateOf(false) }
    var alturaCabecera by remember { mutableStateOf(96.dp) }
    val densidad = LocalDensity.current
    val descripcionTirador = stringResource(R.string.panel_deslizar_mapa)
    val descripcionEstado = stringResource(if (hoja.currentValue == SheetValue.Expanded)
        R.string.panel_expandido else R.string.panel_recogido)
    // Cancelar el arrastre del padre puede dejar unos píxeles de desplazamiento; se recupera el anclaje.
    LaunchedEffect(tocandoContenido, scrollContenido.isScrollInProgress) {
        if (mantenerExpandido && !tocandoContenido && !scrollContenido.isScrollInProgress) {
            mantenerExpandido = false
            hoja.expand()
        }
    }

    BoxWithConstraints(modifier.fillMaxSize().imePadding()) {
        val altoDisponible = constraints.maxHeight.toFloat()
        val desplazamientoHoja = runCatching { hoja.requireOffset() }.getOrNull()?.takeIf { it.isFinite() }
            ?: (altoDisponible - with(densidad) { alturaCabecera.toPx() })
        val margenMapa = with(densidad) { (altoDisponible - desplazamientoHoja).coerceAtLeast(0f).toDp() } + 8.dp
        BottomSheetScaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = estadoPanel,
            sheetPeekHeight = alturaCabecera,
            sheetMaxWidth = androidx.compose.ui.unit.Dp.Unspecified,
            sheetContainerColor = MaterialTheme.colorScheme.surface,
            sheetSwipeEnabled = !tocandoContenido && !scrollContenido.isScrollInProgress,
            sheetDragHandle = {
                CabeceraPanelInicio(uiState.rol, Modifier
                    .onSizeChanged { alturaCabecera = with(densidad) { it.height.toDp() } }
                    .semantics {
                        contentDescription = descripcionTirador
                        stateDescription = descripcionEstado
                    })
            },
            sheetContent = {
                // El contacto se observa sin consumir eventos: los campos y su scroll siguen funcionando.
                val cuerpo = Modifier.fillMaxHeight().pointerInput(Unit) {
                    awaitEachGesture {
                        try {
                            awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Initial)
                            mantenerExpandido = hoja.currentValue == SheetValue.Expanded
                            tocandoContenido = true
                            do {
                                val evento = awaitPointerEvent(PointerEventPass.Initial)
                            } while (evento.changes.any { it.pressed })
                        } finally { tocandoContenido = false }
                    }
                }.verticalScroll(scrollContenido)
                if (uiState.rol == Rol.PASAJERO) {
                    PanelPasajero(
                        origen = uiState.origen, destino = uiState.destino,
                        viajeProgramado = uiState.viajeProgramado,
                        resumenProgramado = stringResource(R.string.fecha_y_hora,
                            fechaBusqueda(uiState.salidaProgramadaMillis), horaBusqueda(uiState.salidaProgramadaMillis)),
                        onDestinoChange = viewModel::actualizarDestino,
                        onSeleccionarMomento = onSeleccionarMomento,
                        onIrALaJaveriana = viewModel::irALaJaveriana,
                        onGuardados = onGuardados, onBuscar = onBuscarWheels,
                        modifier = cuerpo, mostrarEncabezado = false)
                } else {
                    PanelConductor(uiState.proximoViaje, onPublicarWheel, onVerViajeConductor,
                        modifier = cuerpo, mostrarEncabezado = false)
                }
            }
        ) {
            Column(Modifier.fillMaxSize()) {
                EncabezadoInicio(
                    subtitulo = stringResource(if (uiState.rol == Rol.PASAJERO) R.string.ui_vamos_en_wheels else R.string.ui_comparte_tu_ruta),
                    rol = uiState.rol,
                    onCambiarRol = { if (!viewModel.cambiarRol()) onQuiereSerConductor() })
                MapaJaveWheels(
                    modifier = Modifier.weight(1f).fillMaxWidth(), altura = null,
                    margenInferiorControles = margenMapa,
                    marcadores = listOf(MarcadorMapa("javeriana", stringResource(R.string.ui_javeriana), POSICION_JAVERIANA)))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun InicioScreenPreview() {
    JaveWheelsTheme { InicioScreen(onQuiereSerConductor = {}) }
}
