package com.javeriana.javewheels.ui.screens.conductor

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.*
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.components.*
import com.javeriana.javewheels.ui.components.InformacionVehiculo
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.viewmodels.VehiculoUiState
import java.util.Calendar

@Composable
fun PublicarWheelScreen(
    onBack: () -> Unit = {},
    onPublicar: (Viaje) -> Unit = {},
    onSeleccionarPestana: (Pestana) -> Unit = {},
    mostrarBarraNavegacion: Boolean = true,
    vehiculoRegistrado: VehiculoUiState = VehiculoUiState()
) {
    val context = LocalContext.current
    val recursos = LocalResources.current
    val focusManager = LocalFocusManager.current
    var origen by rememberSaveable { mutableStateOf("") }
    var destino by rememberSaveable { mutableStateOf(DESTINO_JAVERIANA) }
    var salida by rememberSaveable { mutableLongStateOf(System.currentTimeMillis() + 60 * 60_000L) }
    var aporte by rememberSaveable { mutableStateOf("4000") }
    val vehiculo = vehiculoRegistrado.resumen
    var cupos by rememberSaveable { mutableStateOf(vehiculoRegistrado.cupos.ifBlank { "3" }) }
    var modoBuseta by rememberSaveable { mutableStateOf(true) }
    var puntoRecogida by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf("") }
    val capacidad = vehiculoRegistrado.cupos.toIntOrNull() ?: 9

    fun elegirFecha() {
        val fecha = Calendar.getInstance().apply { timeInMillis = salida }
        DatePickerDialog(context, { _, anio, mes, dia ->
            salida = Calendar.getInstance().apply {
                timeInMillis = salida
                set(Calendar.YEAR, anio)
                set(Calendar.MONTH, mes)
                set(Calendar.DAY_OF_MONTH, dia)
            }.timeInMillis
            error = ""
        }, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH)).apply {
            datePicker.minDate = System.currentTimeMillis()
        }.show()
    }

    fun elegirHora() {
        val fecha = Calendar.getInstance().apply { timeInMillis = salida }
        TimePickerDialog(context, { _, hora, minuto ->
            salida = Calendar.getInstance().apply {
                timeInMillis = salida
                set(Calendar.HOUR_OF_DAY, hora)
                set(Calendar.MINUTE, minuto)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            error = ""
        }, fecha.get(Calendar.HOUR_OF_DAY), fecha.get(Calendar.MINUTE), false).show()
    }

    Column(Modifier.fillMaxSize().imePadding()) {
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EncabezadoViajes(stringResource(R.string.ui_publicar_wheel), onBack, modifier = Modifier)
            Text(stringResource(R.string.ui_completa_el_recorrido_y_los_detalles_de_tu_viaje),
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            SeccionPublicacion(stringResource(R.string.ui_recorrido)) {
                CampoTexto(stringResource(R.string.ui_origen), origen, { origen = it; error = "" },
                    placeholder = stringResource(R.string.ui_direccion_o_punto_de_salida), obligatorio = true,
                    hayError = error.isNotEmpty() && origen.isBlank())
                CampoTexto(stringResource(R.string.ui_destino), destino, { destino = it; error = "" },
                    placeholder = stringResource(R.string.ui_a_donde_vas), obligatorio = true,
                    hayError = error.isNotEmpty() && destino.isBlank())
            }
            SeccionPublicacion(stringResource(R.string.ui_detalles_del_viaje)) {
                BotonSecundario(stringResource(R.string.ui_fecha_1_s, fechaBusqueda(salida)), onClick = { elegirFecha() })
                BotonSecundario(stringResource(R.string.ui_hora_1_s, horaBusqueda(salida)), onClick = { elegirHora() })
                CampoTexto(stringResource(R.string.ui_aporte_por_pasajero_cop), aporte,
                    { aporte = it.filter { caracter -> caracter.isDigit() }; error = "" },
                    tipoTeclado = KeyboardType.Number, obligatorio = true,
                    hayError = error.isNotEmpty() && (aporte.toIntOrNull() ?: 0) <= 0)
                Text(stringResource(R.string.ui_vehiculo_registrado), style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary)
                InformacionVehiculo(vehiculo)
                CampoTexto(stringResource(R.string.ui_cupos_disponibles), cupos,
                    { cupos = it.filter { caracter -> caracter.isDigit() }.take(1); error = "" },
                    tipoTeclado = KeyboardType.Number, obligatorio = true,
                    hayError = error.isNotEmpty() && (cupos.toIntOrNull() ?: 0) !in 1..capacidad)
                Text(stringResource(R.string.ui_hasta_1_s_cupos_segun_tu_vehiculo, capacidad),
                    style = MaterialTheme.typography.bodySmall)
            }
            SeccionPublicacion(stringResource(R.string.ui_modo_buseta)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.ui_recoger_pasajeros_en_puntos_intermedios), Modifier.weight(1f))
                    Switch(checked = modoBuseta, onCheckedChange = { modoBuseta = it; error = "" })
                }
                if (modoBuseta) CampoTexto(stringResource(R.string.ui_punto_de_recogida), puntoRecogida,
                    { puntoRecogida = it; error = "" }, placeholder = stringResource(R.string.ui_direccion_de_encuentro),
                    obligatorio = true, hayError = error.isNotEmpty() && puntoRecogida.isBlank())
            }
            MensajeError(error)
            BotonPrincipal(stringResource(R.string.ui_publicar_wheel), onClick = {
                val aportePesos = aporte.toIntOrNull()
                val cuposOfrecidos = cupos.toIntOrNull()
                error = when {
                    origen.isBlank() || destino.isBlank() -> recursos.getString(R.string.ui_completa_el_origen_y_el_destino)
                    origen.trim().equals(destino.trim(), ignoreCase = true) -> recursos.getString(R.string.ui_el_origen_y_el_destino_deben_ser_diferentes)
                    salida <= System.currentTimeMillis() -> recursos.getString(R.string.ui_selecciona_una_fecha_y_hora_futuras)
                    aportePesos == null || aportePesos <= 0 -> recursos.getString(R.string.ui_escribe_un_aporte_valido_mayor_a_cero)
                    vehiculo.isBlank() -> recursos.getString(R.string.ui_completa_los_datos_del_vehiculo)
                    cuposOfrecidos == null || cuposOfrecidos !in 1..capacidad -> recursos.getString(R.string.ui_ofrece_entre_1_y_1_s_cupos, capacidad)
                    modoBuseta && puntoRecogida.isBlank() -> recursos.getString(R.string.ui_escribe_el_punto_de_recogida)
                    else -> ""
                }
                if (error.isEmpty()) {
                    focusManager.clearFocus()
                    onPublicar(Viaje(
                        origen = origen.trim(), destino = destino.trim(),
                        fecha = fechaBusqueda(salida), hora = horaBusqueda(salida),
                        cuposOcupados = 0, cuposTotales = cuposOfrecidos!!,
                        estado = "Programado", aporte = formatoPesos(aportePesos!!),
                        vehiculo = vehiculo.trim(), modoBuseta = modoBuseta,
                        puntoRecogida = if (modoBuseta) puntoRecogida.trim() else origen.trim()
                    ))
                }
            })
            BotonTexto(stringResource(R.string.ui_cancelar_publicacion), onClick = {
                focusManager.clearFocus()
                onBack()
            })
        }
        if (mostrarBarraNavegacion) BarraNavegacion(Pestana.MisViajes, onSeleccionarPestana)
    }
}

@Composable
private fun SeccionPublicacion(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
    )) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary)
            contenido()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PublicarWheelScreenPreview() {
    JaveWheelsTheme { PublicarWheelScreen() }
}
