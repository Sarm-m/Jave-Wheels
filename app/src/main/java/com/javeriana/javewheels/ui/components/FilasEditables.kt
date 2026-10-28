package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun FilaConBoton(
    etiqueta: String,
    textoBoton: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    valor: String = ""
) {
    FilaAdaptable(
        modifier = modifier.padding(vertical = 6.dp),
        principal = {
            Column {
                Text(etiqueta, style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary)
                if (valor.isNotBlank()) Text(valor, style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        secundario = { BotonPrincipal(textoBoton, onClick, compacto = true) }
    )
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
    hayError: Boolean = false,
    mensajeApoyo: String? = null,
    contenidoExtra: @Composable () -> Unit = {}
) {
    var editando by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(hayError) { if (hayError) editando = true }
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.fillMaxWidth()) {
        FilaConBoton(
            etiqueta = etiqueta,
            valor = if (editando || esContrasena) "" else valor,
            textoBoton = if (editando) textoListo else textoEditar,
            onClick = {
                if (editando) focusManager.clearFocus()
                editando = !editando
            }
        )
        if (editando) {
            CampoTexto(
                etiqueta = etiquetaCampo,
                valor = valor,
                alCambiar = alCambiar,
                placeholder = placeholder,
                tipoTeclado = tipoTeclado,
                esContrasena = esContrasena,
                obligatorio = obligatorio,
                hayError = hayError,
                mensajeApoyo = mensajeApoyo
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
            FilaConBoton(etiqueta = stringResource(R.string.ui_datos_de_perfil), textoBoton = stringResource(R.string.editar), onClick = {})
            FilaEditable(
                etiqueta = stringResource(R.string.ui_placa_del_automovil),
                valor = "ABC123",
                alCambiar = {},
                textoEditar = stringResource(R.string.editar),
                textoListo = stringResource(R.string.listo),
                placeholder = stringResource(R.string.ui_escribe_la_placa_del_carro)
            )
            FilaEditable(
                etiqueta = stringResource(R.string.ui_contrasena),
                valor = "",
                alCambiar = {},
                textoEditar = stringResource(R.string.editar),
                textoListo = stringResource(R.string.listo),
                esContrasena = true,
                obligatorio = false
            )
        }
    }
}
