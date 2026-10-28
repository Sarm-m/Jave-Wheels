package com.javeriana.javewheels.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.javeriana.javewheels.R
import com.javeriana.javewheels.entities.Rol

@Composable
fun tituloRol(rol: Rol): String = stringResource(
    if (rol == Rol.PASAJERO) R.string.rol_pasajero else R.string.rol_conductor
)

@Composable
fun SelectorRol(rol: Rol, onCambiarRol: () -> Unit, modifier: Modifier = Modifier) {
    AssistChip(
        onClick = onCambiarRol,
        label = { Text(tituloRol(rol)) },
        leadingIcon = { Icon(Icons.Default.SwapHoriz, stringResource(R.string.ui_cambiar_rol)) },
        modifier = modifier
    )
}
