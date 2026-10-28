package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/** Apila las secciones cuando el ancho o la escala de letra no permiten una fila legible. */
@Composable
fun FilaAdaptable(
    modifier: Modifier = Modifier,
    principal: @Composable () -> Unit,
    secundario: @Composable () -> Unit
) {
    val escalaLetra = LocalDensity.current.fontScale
    BoxWithConstraints(modifier.fillMaxWidth()) {
        if (maxWidth / escalaLetra < 320.dp) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                principal()
                secundario()
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(Modifier.weight(1f)) { principal() }
                secundario()
            }
        }
    }
}
