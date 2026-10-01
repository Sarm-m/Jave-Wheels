package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    hayError: Boolean = false
) {
    // Estado visual (mostrar u ocultar la contraseña).
    var mostrarContrasena by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = valor,
            onValueChange = alCambiar,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            singleLine = true,
            isError = hayError,
            shape = RoundedCornerShape(12.dp),
            // Tipo de teclado: correo, números, teléfono...
            keyboardOptions = KeyboardOptions(
                keyboardType = if (esContrasena) KeyboardType.Password else tipoTeclado
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
                            contentDescription = if (mostrarContrasena) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
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
                etiqueta = "Correo institucional",
                valor = "",
                alCambiar = {},
                placeholder = "nombre@javeriana.edu.co"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                etiqueta = "Contraseña",
                valor = "clave123",
                alCambiar = {},
                esContrasena = true
            )
        }
    }
}
