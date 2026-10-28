package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// Campo de texto con etiqueta arriba.
// Recibe el valor y un callback (String) -> Unit: el campo no guarda
// el texto, lo guarda el ViewModel.
// ============================================================

@Composable
fun CampoTexto(
    etiqueta: String,
    valor: String,
    alCambiar: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    esContrasena: Boolean = false,
    tipoTeclado: KeyboardType = KeyboardType.Text,
    hayError: Boolean = false,
    obligatorio: Boolean = false,   // true = muestra un * rojo antes de la etiqueta
    iconoFinal: ImageVector? = null,   // ícono al final del campo (ej. lupa del buscador)
    descripcionIconoFinal: String? = null,
    mensajeApoyo: String? = null,
    accionTeclado: ImeAction = ImeAction.Next
) {
    // Estado visual (mostrar u ocultar la contraseña).
    var mostrarContrasena by rememberSaveable { mutableStateOf(false) }
    val foco = LocalFocusManager.current
    val colorError = MaterialTheme.colorScheme.error

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = valor,
            onValueChange = alCambiar,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            label = {
                Text(buildAnnotatedString {
                    append(etiqueta.ifBlank { placeholder })
                    if (obligatorio) withStyle(SpanStyle(color = colorError)) { append("\u00A0*") }
                })
            },
            supportingText = if (mensajeApoyo != null || hayError) {
                { Text(mensajeApoyo ?: stringResource(R.string.error_campo)) }
            } else null,
            singleLine = true,
            isError = hayError,
            shape = RoundedCornerShape(12.dp),
            // Tipo de teclado: correo, números, teléfono...
            keyboardOptions = KeyboardOptions(
                keyboardType = if (esContrasena) KeyboardType.Password else tipoTeclado,
                imeAction = accionTeclado
            ),
            keyboardActions = KeyboardActions(
                onNext = { foco.moveFocus(FocusDirection.Down) },
                onDone = { foco.clearFocus() }
            ),
            // Si es contraseña y está oculta, se muestran puntos (••••)
            visualTransformation = if (esContrasena && !mostrarContrasena) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            // Ojito para mostrar / ocultar la contraseña
            trailingIcon = {
                if (esContrasena) {
                    IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                        Icon(
                            imageVector = if (mostrarContrasena) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (mostrarContrasena) stringResource(R.string.ui_ocultar_contrasena) else stringResource(R.string.ui_mostrar_contrasena)
                        )
                    }
                } else if (iconoFinal != null) {
                    Icon(imageVector = iconoFinal, contentDescription = descripcionIconoFinal)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CampoTextoPreview() {
    JaveWheelsTheme {
        Column(modifier = Modifier.padding(24.dp)) {
            CampoTexto(
                etiqueta = stringResource(R.string.ui_correo_institucional),
                valor = "",
                alCambiar = {},
                placeholder = stringResource(R.string.ui_nombre_javeriana_edu_co)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                etiqueta = stringResource(R.string.ui_contrasena),
                valor = "clave123",
                alCambiar = {},
                esContrasena = true
            )
        }
    }
}
