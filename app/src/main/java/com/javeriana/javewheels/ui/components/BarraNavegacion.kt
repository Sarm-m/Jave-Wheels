package com.javeriana.javewheels.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.javeriana.javewheels.navigation.Pestana
import com.javeriana.javewheels.ui.theme.JWBlanco
import com.javeriana.javewheels.ui.theme.JWCelesteContenedor
import com.javeriana.javewheels.ui.theme.JWGrisAzulado
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// Barra de navegación inferior
// ============================================================

@Composable
fun BarraNavegacion(
    pestanaActual: Pestana,
    onSeleccionar: (Pestana) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = JWBlanco
    ) {
        Pestana.entries.forEach { pestana ->
            val seleccionada = pestana == pestanaActual
            NavigationBarItem(
                selected = seleccionada,
                onClick = { onSeleccionar(pestana) },
                icon = {
                    Icon(
                        imageVector = if (seleccionada) pestana.iconoSeleccionado else pestana.icono,
                        contentDescription = pestana.titulo
                    )
                },
                label = { Text(pestana.titulo, style = MaterialTheme.typography.labelMedium) },
                // Colores: activo = azul con fondo celeste, inactivo = gris azulado
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = JWCelesteContenedor,
                    unselectedIconColor = JWGrisAzulado,
                    unselectedTextColor = JWGrisAzulado
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BarraNavegacionPreview() {
    JaveWheelsTheme {
        BarraNavegacion(pestanaActual = Pestana.Inicio, onSeleccionar = {})
    }
}
