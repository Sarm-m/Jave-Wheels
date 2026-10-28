package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TripOrigin
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.*
import com.javeriana.javewheels.ui.components.*
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.viewmodels.InicioViewModel
import java.util.Calendar

/** Dos pantallas de búsqueda comparten formulario; Programar agrega fecha y hora. */
@Composable
fun BuscarMomentoScreen(
    programado: Boolean,
    viewModel: InicioViewModel,
    onVolver: () -> Unit,
    onBuscar: (String, String, Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val estado by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val recursos = LocalResources.current
    val focos = remember { List(2) { FocusRequester() } }
    val foco = LocalFocusManager.current
    val teclado = LocalSoftwareKeyboardController.current
    // El borrador pertenece a esta pantalla: Atrás no cambia la búsqueda confirmada.
    var origen by rememberSaveable { mutableStateOf(estado.origen) }
    var destino by rememberSaveable { mutableStateOf(estado.destino) }
    var salida by rememberSaveable { mutableLongStateOf(estado.salidaProgramadaMillis) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    fun buscarWheels() {
        foco.clearFocus(force = true)
        teclado?.hide()
        val momento = if (programado) salida else System.currentTimeMillis()
        error = when {
            origen.isBlank() -> recursos.getString(R.string.ui_escribe_el_origen_del_viaje)
            destino.isBlank() -> recursos.getString(R.string.ui_escribe_el_destino_del_viaje)
            origen.trim().equals(destino.trim(), ignoreCase = true) -> recursos.getString(R.string.ui_el_origen_y_el_destino_deben_ser_diferentes)
            programado && momento <= System.currentTimeMillis() -> recursos.getString(R.string.ui_selecciona_una_fecha_y_hora_futuras)
            else -> null
        }
        if (error == null) {
            viewModel.actualizarBusqueda(origen, destino, programado, momento)
            onBuscar(origen.trim(), destino.trim(), momento)
        }
    }

    fun elegirFecha() {
        val fecha = Calendar.getInstance().apply { timeInMillis = salida }
        DatePickerDialog(context, { _, anio, mes, dia ->
            salida = Calendar.getInstance().apply {
                timeInMillis = salida
                set(Calendar.YEAR, anio); set(Calendar.MONTH, mes); set(Calendar.DAY_OF_MONTH, dia)
                set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            error = null
        }, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH)).apply {
            datePicker.minDate = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            }.timeInMillis
        }.show()
    }

    fun elegirHora() {
        val fecha = Calendar.getInstance().apply { timeInMillis = salida }
        TimePickerDialog(context, { _, hora, minuto ->
            salida = Calendar.getInstance().apply {
                timeInMillis = salida
                set(Calendar.HOUR_OF_DAY, hora); set(Calendar.MINUTE, minuto)
                set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            error = null
        }, fecha.get(Calendar.HOUR_OF_DAY), fecha.get(Calendar.MINUTE), true).show()
    }

    Column(modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState())) {
        EncabezadoViajes(if (programado) stringResource(R.string.ui_programar_busqueda) else stringResource(R.string.ui_buscar_ahora), onVolver)
        MapaJaveWheels(altura = 170.dp, mostrarCentrar = false)
        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(if (programado) stringResource(R.string.ui_cuando_quieres_viajar) else stringResource(R.string.ui_a_donde_vas_ahora),
                style = MaterialTheme.typography.headlineSmall)
            Text(if (programado) stringResource(R.string.ui_elige_fecha_y_hora_buscaremos_salidas_durante_la_hora_siguiente)
                else stringResource(R.string.ui_buscaremos_wheels_que_salen_durante_los_proximos_60_minutos))
            OutlinedTextField(origen, { origen = it; error = null }, Modifier.fillMaxWidth().focusRequester(focos[0]),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focos[1].requestFocus() }),
                label = { Text(stringResource(R.string.ui_origen)) }, singleLine = true,
                isError = error != null && origen.isBlank(),
                leadingIcon = { Icon(Icons.Default.TripOrigin, contentDescription = null) })
            TextButton(onClick = { origen = "Mi ubicación"; error = null }) { Text(stringResource(R.string.ui_usar_mi_ubicacion)) }
            OutlinedTextField(destino, { destino = it; error = null }, Modifier.fillMaxWidth().focusRequester(focos[1]),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { buscarWheels() }),
                label = { Text(stringResource(R.string.ui_destino)) }, singleLine = true,
                isError = error != null && destino.isBlank(),
                leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) })
            if (!destino.trim().equals(DESTINO_JAVERIANA, ignoreCase = true) &&
                !destino.trim().equals("Pontificia Universidad Javeriana", ignoreCase = true)) {
                TextButton(onClick = { destino = DESTINO_JAVERIANA; error = null }) { Text(stringResource(R.string.ui_ir_a_la_javeriana)) }
            }
            if (programado) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ChipOpcion(stringResource(R.string.ui_hoy), Icons.Default.CalendarMonth, false, {
                        salida = salidaEnDias(0, minutosDelDia(salida)); error = null
                    })
                    ChipOpcion(stringResource(R.string.ui_manana), Icons.Default.CalendarMonth, false, {
                        salida = salidaEnDias(1, minutosDelDia(salida)); error = null
                    })
                }
                OutlinedButton(onClick = { elegirFecha() }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.ui_fecha_1_s, fechaBusqueda(salida)))
                }
                OutlinedButton(onClick = { elegirHora() }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Schedule, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.ui_hora_1_s, horaBusqueda(salida)))
                }
            } else {
                Card(Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.ui_salida_ahora_proximos_60_minutos), Modifier.padding(16.dp))
                }
            }
            error?.let { MensajeError(it) }
            BotonPrincipal(if (programado) stringResource(R.string.ui_buscar_wheels_programados) else stringResource(R.string.ui_buscar_wheels_ahora), onClick = ::buscarWheels)
            Text(stringResource(R.string.ui_datos_locales_de_demostracion_mi_ubicacion_no_utiliza_gps_hay_via),
                style = MaterialTheme.typography.bodySmall)
        }
    }
}
