package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.theme.JWAzul
import com.javeriana.javewheels.ui.theme.JWCelesteContenedor
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun AvatarUsuario(
    modifier: Modifier = Modifier,
    tamano: Dp = 44.dp,
    contentDescription: String? = null
) {
    Box(
        modifier = modifier.size(tamano).clip(CircleShape).background(JWCelesteContenedor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = contentDescription,
            tint = JWAzul,
            modifier = Modifier.size(tamano * 0.6f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarUsuarioPreview() {
    JaveWheelsTheme { AvatarUsuario() }
}
