package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun FilaConBoton(
    etiqueta: String,
    textoBoton: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    valor: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            if (valor.isNotBlank()) {
                Text(
                    text = valor,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        BotonPrincipal(
            texto = textoBoton,
            onClick = onClick,
            compacto = true
        )
    }
}

/**
 * Fila con botón que, al tocarlo, despliega el CampoTexto debajo.
 * Al tocar de nuevo (botón con [textoListo]) el campo se oculta.
 * "editando" es estado puramente visual, por eso vive aquí y no en el ViewModel.
 *  - [textoEditar] / [textoListo]: texto del botón según el momento.
 *  - [etiquetaCampo]: etiqueta del campo desplegado (por defecto, la de la fila).
 *  - [esContrasena]: el campo oculta lo escrito y la fila nunca muestra el valor.
 *  - [obligatorio]: false quita el * rojo (campos opcionales).
 *  - [contenidoExtra]: se dibuja justo debajo del campo desplegado
 *    (ej. el campo "Confirmar contraseña").
 */
@Composable
fun FilaEditable(
    etiqueta: String,
    valor: String,
    alCambiar: (String) -> Unit,
    textoEditar: String,
    textoListo: String,
    modifier: Modifier = Modifier,
    etiquetaCampo: String = etiqueta,
    placeholder: String = "",
    tipoTeclado: KeyboardType = KeyboardType.Text,
    esContrasena: Boolean = false,
    obligatorio: Boolean = true,
    contenidoExtra: @Composable () -> Unit = {}
) {
    var editando by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        FilaConBoton(
            etiqueta = etiqueta,
            valor = if (editando || esContrasena) "" else valor,
            textoBoton = if (editando) textoListo else textoEditar,
            onClick = { editando = !editando }
        )
        if (editando) {
            CampoTexto(
                etiqueta = etiquetaCampo,
                valor = valor,
                alCambiar = alCambiar,
                placeholder = placeholder,
                tipoTeclado = tipoTeclado,
                esContrasena = esContrasena,
                obligatorio = obligatorio
            )
            contenidoExtra()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilasEditablesPreview() {
    JaveWheelsTheme {
        Column(modifier = Modifier.padding(24.dp)) {
            FilaConBoton(etiqueta = "Datos de perfil", textoBoton = "Editar", onClick = {})
            FilaEditable(
                etiqueta = "Placa del automóvil",
                valor = "ABC123",
                alCambiar = {},
                textoEditar = "Editar",
                textoListo = "Listo",
                placeholder = "Escribe la placa del carro"
            )
            FilaEditable(
                etiqueta = "Contraseña",
                valor = "",
                alCambiar = {},
                textoEditar = "Editar",
                textoListo = "Listo",
                esContrasena = true,
                obligatorio = false
            )
        }
    }
}