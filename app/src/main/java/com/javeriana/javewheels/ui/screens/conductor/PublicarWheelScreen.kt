package com.javeriana.javewheels.ui.screens.conductor

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.entities.*
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.components.*
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import com.javeriana.javewheels.ui.theme.JWCelesteSuave
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
            EncabezadoViajes("Publicar Wheel", onBack, modifier = Modifier)
            Text("Completa el recorrido y los detalles de tu viaje.",
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            SeccionPublicacion("Recorrido") {
                CampoTexto("Origen", origen, { origen = it; error = "" },
                    placeholder = "Dirección o punto de salida", obligatorio = true)
                CampoTexto("Destino", destino, { destino = it; error = "" },
                    placeholder = "¿A dónde vas?", obligatorio = true)
            }
            SeccionPublicacion("Detalles del viaje") {
                BotonSecundario("Fecha: ${fechaBusqueda(salida)}", onClick = { elegirFecha() })
                BotonSecundario("Hora: ${horaBusqueda(salida)}", onClick = { elegirHora() })
                CampoTexto("Aporte por pasajero (COP)", aporte,
                    { aporte = it.filter { caracter -> caracter.isDigit() }; error = "" },
                    tipoTeclado = KeyboardType.Number, obligatorio = true)
                Text("Vehículo registrado", style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary)
                Text(vehiculo.ifBlank { "Registra tu vehículo desde Perfil." })
                CampoTexto("Cupos disponibles", cupos,
                    { cupos = it.filter { caracter -> caracter.isDigit() }.take(1); error = "" },
                    tipoTeclado = KeyboardType.Number, obligatorio = true)
                Text("Hasta $capacidad cupos según tu vehículo.",
                    style = MaterialTheme.typography.bodySmall)
            }
            SeccionPublicacion("Modo Buseta") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Recoger pasajeros en puntos intermedios", Modifier.weight(1f))
                    Switch(checked = modoBuseta, onCheckedChange = { modoBuseta = it; error = "" })
                }
                if (modoBuseta) CampoTexto("Punto de recogida", puntoRecogida,
                    { puntoRecogida = it; error = "" }, placeholder = "Dirección de encuentro",
                    obligatorio = true)
            }
            MensajeError(error)
            BotonPrincipal("Publicar Wheel", onClick = {
                val aportePesos = aporte.toIntOrNull()
                val cuposOfrecidos = cupos.toIntOrNull()
                error = when {
                    origen.isBlank() || destino.isBlank() -> "Completa el origen y el destino."
                    origen.trim().equals(destino.trim(), ignoreCase = true) -> "El origen y el destino deben ser diferentes."
                    salida <= System.currentTimeMillis() -> "Selecciona una fecha y hora futuras."
                    aportePesos == null || aportePesos <= 0 -> "Escribe un aporte válido mayor a cero."
                    vehiculo.isBlank() -> "Completa los datos del vehículo."
                    cuposOfrecidos == null || cuposOfrecidos !in 1..capacidad -> "Ofrece entre 1 y $capacidad cupos."
                    modoBuseta && puntoRecogida.isBlank() -> "Escribe el punto de recogida."
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
            BotonTexto("Cancelar publicación", onClick = {
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
        containerColor = JWCelesteSuave
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
