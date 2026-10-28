package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun BarraNavegacion(
    pestanaActual: Pestana,
    onSeleccionar: (Pestana) -> Unit,
    modifier: Modifier = Modifier
) {
    val medidor = rememberTextMeasurer()
    val densidad = LocalDensity.current
    val letraGrande = LocalDensity.current.fontScale > 1.2f
    BoxWithConstraints(modifier.fillMaxWidth()) {
        val anchoEtiqueta = maxWidth / Pestana.entries.size - 4.dp
        NavigationBar(
            modifier = Modifier.fillMaxWidth().heightIn(min = if (letraGrande) 104.dp else 80.dp),
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Pestana.entries.forEach { pestana ->
                val seleccionada = pestana == pestanaActual
                val titulo = stringResource(pestana.titulo)
                val estilo = MaterialTheme.typography.labelMedium
                val anchoPalabra = with(densidad) {
                    medidor.measure(titulo, style = estilo, softWrap = false).size.width.toDp() + 2.dp
                }
                val anchoTexto = if (' ' in titulo) anchoEtiqueta else maxOf(anchoEtiqueta, anchoPalabra)
                NavigationBarItem(
                    selected = seleccionada,
                    onClick = { onSeleccionar(pestana) },
                    icon = { Icon(if (seleccionada) pestana.iconoSeleccionado else pestana.icono, null) },
                    label = {
                        // Aprovecha el ancho de cada destino y reserva dos líneas con letra ampliada.
                        Text(titulo, Modifier.requiredWidth(anchoTexto),
                            style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.Center,
                            minLines = if (letraGrande) 2 else 1)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, fontScale = 1.5f)
@Composable
private fun BarraNavegacionPreview() {
    JaveWheelsTheme { BarraNavegacion(Pestana.Inicio, {}) }
}
