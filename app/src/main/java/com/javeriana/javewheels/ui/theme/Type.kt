package com.javeriana.javewheels.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ============================================================
// Tipografía de JaveWheels
// FontFamily.Default en Android es Roboto (la fuente principal del diseño).
// Los tamaños salen de las variables "tipografia/tamano/...".
// Recordatorio: .sp para textos, .dp para tamaños y espacios.
// ============================================================

val Typography = Typography(
    // Títulos grandes: "¡Hola, javeriano!", "Crea tu cuenta"...
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 30.sp,
        lineHeight = 38.sp
    ),
    // Títulos de paneles: "¿A dónde vas?", "Comparte tu camino"
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 26.sp,
        lineHeight = 32.sp
    ),
    // Nombre de la marca y saludos
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    // Subtítulos: "Contacto", "Tu vehículo"...
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp
    ),
    // Texto normal
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    // Texto de los botones
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    // Etiquetas pequeñas (barra de navegación, chips)
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)
