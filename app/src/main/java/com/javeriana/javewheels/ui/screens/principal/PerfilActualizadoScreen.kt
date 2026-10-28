package com.javeriana.javewheels.ui.screens.principal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.components.ConfirmacionActualizacion
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun PerfilActualizadoScreen(
    onIrAlMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConfirmacionActualizacion(
        titulo = stringResource(R.string.ui_tu_perfil_fue_actualizado_correctamente),
        descripcion = stringResource(R.string.ui_perfil_actualizado),
        textoBoton = stringResource(R.string.ui_ir_al_menu_principal),
        onContinuar = onIrAlMenu,
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun PerfilActualizadoScreenPreview() {
    JaveWheelsTheme {
        PerfilActualizadoScreen(onIrAlMenu = {})
    }
}
