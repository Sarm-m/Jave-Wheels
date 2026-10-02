package com.javeriana.javewheels.ui.screens.principal

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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.entities.*
import com.javeriana.javewheels.ui.components.*
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
    // El borrador pertenece a esta pantalla: Atrás no cambia la búsqueda confirmada.
    var origen by rememberSaveable { mutableStateOf(estado.origen) }
    var destino by rememberSaveable { mutableStateOf(estado.destino) }
    var salida by rememberSaveable { mutableLongStateOf(estado.salidaProgramadaMillis) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

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
        EncabezadoViajes(if (programado) "Programar búsqueda" else "Buscar ahora", onVolver)
        MapaIlustrativo(altura = 170.dp, mostrarCentrar = false,
            textoUbicacion = if (origen == "Mi ubicación") "Tu ubicación" else origen,
            textoDestino = destino.ifBlank { "Destino" })
        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(if (programado) "¿Cuándo quieres viajar?" else "¿A dónde vas ahora?",
                style = MaterialTheme.typography.headlineSmall)
            Text(if (programado) "Elige fecha y hora. Buscaremos salidas durante la hora siguiente."
                else "Buscaremos Wheels que salen durante los próximos 60 minutos.")
            OutlinedTextField(origen, { origen = it; error = null }, Modifier.fillMaxWidth(),
                label = { Text("Origen") }, singleLine = true,
                leadingIcon = { Icon(Icons.Default.TripOrigin, contentDescription = null) })
            TextButton(onClick = { origen = "Mi ubicación"; error = null }) { Text("Usar mi ubicación") }
            OutlinedTextField(destino, { destino = it; error = null }, Modifier.fillMaxWidth(),
                label = { Text("Destino") }, singleLine = true,
                leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) })
            TextButton(onClick = { destino = "Javeriana"; error = null }) { Text("Ir a la Javeriana") }
            if (programado) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ChipOpcion("Hoy", Icons.Default.CalendarMonth, false, {
                        salida = salidaEnDias(0, minutosDelDia(salida)); error = null
                    })
                    ChipOpcion("Mañana", Icons.Default.CalendarMonth, false, {
                        salida = salidaEnDias(1, minutosDelDia(salida)); error = null
                    })
                }
                OutlinedButton(onClick = { elegirFecha() }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Fecha: ${fechaBusqueda(salida)}")
                }
                OutlinedButton(onClick = { elegirHora() }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Schedule, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Hora: ${horaBusqueda(salida)}")
                }
            } else {
                Card(Modifier.fillMaxWidth()) {
                    Text("Salida: ahora · próximos 60 minutos", Modifier.padding(16.dp))
                }
            }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            BotonPrincipal(if (programado) "Buscar Wheels programados" else "Buscar Wheels ahora", onClick = {
                val momento = if (programado) salida else System.currentTimeMillis()
                error = when {
                    origen.isBlank() -> "Escribe el origen del viaje."
                    destino.isBlank() -> "Escribe el destino del viaje."
                    origen.trim().equals(destino.trim(), ignoreCase = true) -> "El origen y el destino deben ser diferentes."
                    programado && momento <= System.currentTimeMillis() -> "Selecciona una fecha y hora futuras."
                    else -> null
                }
                if (error == null) {
                    viewModel.actualizarBusqueda(origen, destino, programado, momento)
                    onBuscar(origen.trim(), destino.trim(), momento)
                }
            })
            Text("Datos locales de demostración. Mi ubicación no utiliza GPS. Hay viajes para ahora, mañana y pasado mañana.",
                style = MaterialTheme.typography.bodySmall)
        }
    }
}
