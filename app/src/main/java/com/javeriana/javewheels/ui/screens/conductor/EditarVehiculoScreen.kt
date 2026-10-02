package com.javeriana.javewheels.ui.screens.conductor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.components.BotonPrincipal
import com.javeriana.javewheels.ui.components.FilaEditable
import com.javeriana.javewheels.ui.components.LogoJaveWheels
import com.javeriana.javewheels.ui.components.MarcaJaveWheels
import com.javeriana.javewheels.ui.components.MensajeError
import com.javeriana.javewheels.viewmodels.VehiculoViewModel

@Composable
fun EditarVehiculoScreen(
    viewModel: VehiculoViewModel,
    onGuardado: () -> Unit,
    modifier: Modifier = Modifier
) {
    val estado by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        MarcaJaveWheels()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¡Hola, javeriano!",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        LogoJaveWheels(
            tamano = 96.dp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        FilaEditable(
            etiqueta = "Número telefónico",
            valor = estado.celular,
            alCambiar = viewModel::actualizarCelular,
            textoEditar = "Editar",
            textoListo = "Listo",
            placeholder = "Escribe tu número de celular",
            tipoTeclado = KeyboardType.Phone
        )
        FilaEditable(
            etiqueta = "Placa del automóvil",
            etiquetaCampo = "Placa del vehículo",
            valor = estado.placa,
            alCambiar = viewModel::actualizarPlaca,
            textoEditar = "Editar",
            textoListo = "Listo",
            placeholder = "Escribe la placa del carro"
        )
        FilaEditable(
            etiqueta = "Marca del automóvil",
            valor = estado.marca,
            alCambiar = viewModel::actualizarMarca,
            textoEditar = "Editar",
            textoListo = "Listo",
            placeholder = "Escribe la marca"
        )
        FilaEditable(
            etiqueta = "Color del vehículo",
            valor = estado.color,
            alCambiar = viewModel::actualizarColor,
            textoEditar = "Editar",
            textoListo = "Listo",
            placeholder = "Escribe el color"
        )
        FilaEditable(
            etiqueta = "Modelo / Línea",
            valor = estado.modelo,
            alCambiar = viewModel::actualizarModelo,
            textoEditar = "Editar",
            textoListo = "Listo",
            placeholder = "Escribe el modelo o la línea"
        )
        FilaEditable(
            etiqueta = "Año del vehículo",
            valor = estado.anio,
            alCambiar = viewModel::actualizarAnio,
            textoEditar = "Editar",
            textoListo = "Listo",
            placeholder = "Ej. 2020",
            tipoTeclado = KeyboardType.Number
        )
        FilaEditable(
            etiqueta = "Cupos del automóvil",
            valor = estado.cupos,
            alCambiar = viewModel::actualizarCupos,
            textoEditar = "Editar",
            textoListo = "Listo",
            placeholder = "Ej. 3",
            tipoTeclado = KeyboardType.Number
        )

        MensajeError(mensaje = estado.mensajeError)
        Spacer(modifier = Modifier.height(12.dp))
        BotonPrincipal(
            texto = "Guardar",
            onClick = { if (viewModel.guardarEdicion()) onGuardado() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(160.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}
