package com.javeriana.javewheels.viewmodels

import com.javeriana.javewheels.entities.ANIO_MINIMO_VEHICULO
import com.javeriana.javewheels.entities.DOMINIO_INSTITUCIONAL
import com.javeriana.javewheels.entities.MINIMO_CONTRASENA
import java.util.Calendar

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


/** true si la placa tiene 3 letras y 3 números (ABC123). */
fun esPlacaValida(placa: String): Boolean {
    return Regex("^[A-Z]{3}[0-9]{3}$").matches(placa.trim().uppercase())
}

/** true si el año está entre ANIO_MINIMO_VEHICULO y el año que viene. */
fun esAnioValido(anio: String): Boolean {
    val valor = anio.toIntOrNull() ?: return false
    val anioMaximo = Calendar.getInstance().get(Calendar.YEAR) + 1
    return valor in ANIO_MINIMO_VEHICULO..anioMaximo
}

/** true si el celular tiene 10 dígitos y empieza por 3 (los espacios se ignoran). */
fun esCelularValido(celular: String): Boolean {
    val digitos = celular.filter { it.isDigit() }
    return digitos.length == 10 && digitos.startsWith("3")
}

/** true si el nombre tiene al menos 3 caracteres. */
fun esNombreValido(nombre: String): Boolean {
    return nombre.trim().length >= 3
}