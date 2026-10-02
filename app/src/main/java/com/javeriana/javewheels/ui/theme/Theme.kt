package com.javeriana.javewheels.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// ============================================================
// Tema de la app
// Conecta los colores de Color.kt con los "roles" de Material 3.
// Así los componentes (Button, OutlinedTextField, NavigationBar...)
// toman automáticamente los colores de JaveWheels.
// Solo hay tema claro porque el diseño es claro.
// ============================================================

private val esquemaClaro = lightColorScheme(
    primary = JWAzul,
    onPrimary = JWBlanco,
    primaryContainer = JWCelesteContenedor,
    onPrimaryContainer = JWAzul,
    secondary = JWAzulSecundario,
    onSecondary = JWBlanco,
    secondaryContainer = JWCelesteSuave,
    onSecondaryContainer = JWAzul,
    tertiary = JWAmarillo,
    onTertiary = JWAzul,
    tertiaryContainer = JWAmarilloSuave,
    onTertiaryContainer = JWAzul,
    background = JWBlanco,
    onBackground = JWTexto,
    surface = JWBlanco,
    onSurface = JWTexto,
    surfaceVariant = JWCelesteSuave,
    onSurfaceVariant = JWGrisAzulado,
    surfaceTint = JWAzul,
    surfaceDim = JWCelesteSuave,
    surfaceBright = JWBlanco,
    surfaceContainerLowest = JWBlanco,
    surfaceContainerLow = JWCelesteSuave,
    surfaceContainer = JWCelesteSuave,
    surfaceContainerHigh = JWCelesteSuave,
    surfaceContainerHighest = JWCelesteSuave,
    inverseSurface = JWAzul,
    inverseOnSurface = JWBlanco,
    inversePrimary = JWCelesteContenedor,
    outline = JWCeleste,
    outlineVariant = JWBordeSuave,
    error = JWError
)

@Composable
fun JaveWheelsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = esquemaClaro,
        typography = Typography,
        content = content
    )
}
