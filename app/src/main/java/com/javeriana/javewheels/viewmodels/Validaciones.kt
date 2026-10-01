package com.javeriana.javewheels.viewmodels

import com.javeriana.javewheels.entities.DOMINIO_INSTITUCIONAL
import com.javeriana.javewheels.entities.MINIMO_CONTRASENA

// ============================================================
// Validaciones compartidas por varios ViewModels.
// Son funciones de Kotlin: reciben un String
// y devuelven true/false.
// ============================================================

/** true si el correo termina en @javeriana.edu.co */
fun esCorreoInstitucional(correo: String): Boolean {
    return correo.trim().endsWith(DOMINIO_INSTITUCIONAL, ignoreCase = true) &&
            correo.trim().length > DOMINIO_INSTITUCIONAL.length
}

/** true si la contraseña tiene mínimo 8 caracteres, letras y números. */
fun esContrasenaValida(contrasena: String): Boolean {
    val tieneLetras = contrasena.any { it.isLetter() }
    val tieneNumeros = contrasena.any { it.isDigit() }
    return contrasena.length >= MINIMO_CONTRASENA && tieneLetras && tieneNumeros
}
