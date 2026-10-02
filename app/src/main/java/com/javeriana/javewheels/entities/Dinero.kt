package com.javeriana.javewheels.entities

import java.text.NumberFormat
import java.util.Locale

fun formatoPesos(valor: Int): String =
    "\$${NumberFormat.getIntegerInstance(Locale.forLanguageTag("es-CO")).format(valor)}"
