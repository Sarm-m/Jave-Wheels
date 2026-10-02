package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.ui.theme.JWCelesteContenedor
import com.javeriana.javewheels.ui.theme.JWCelesteSuave
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

@Composable
fun FilaConAvatar(
    titulo: String,
    descripcionAvatar: String,
    modifier: Modifier = Modifier,
    subtitulo: String = "",
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarUsuario(
            foto = null,
            descripcion = descripcionAvatar,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (subtitulo.isNotBlank()) {
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun BurbujaMensaje(
    texto: String,
    esMio: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = if (esMio) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .widthIn(max = 260.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (esMio) JWCelesteContenedor else JWCelesteSuave)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComponentesChatPreview() {
    JaveWheelsTheme {
        Column(modifier = Modifier.padding(24.dp)) {
            FilaConAvatar(
                titulo = "Camila Rojas",
                subtitulo = "Tú: Hola",
                descripcionAvatar = "Foto de Camila Rojas",
                onClick = {}
            )
            BurbujaMensaje(texto = "Salgo en 2", esMio = false)
            Spacer(modifier = Modifier.width(8.dp))
            BurbujaMensaje(texto = "Ok", esMio = true)
        }
    }
}