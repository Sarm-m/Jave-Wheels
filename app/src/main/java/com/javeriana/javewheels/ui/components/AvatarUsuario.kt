package com.javeriana.javewheels.ui.components

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.javeriana.javewheels.ui.theme.FotoCirculoFondo
import com.javeriana.javewheels.ui.theme.FotoPanelFondo
import com.javeriana.javewheels.ui.theme.FotoSilueta
import com.javeriana.javewheels.ui.theme.JWCelesteContenedor
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AvatarUsuario(
    foto: Uri?,
    descripcion: String,
    modifier: Modifier = Modifier,
    colorFondo: Color = JWCelesteContenedor,
    colorIcono: Color = MaterialTheme.colorScheme.primary
) {
    val contexto = LocalContext.current
    // Carga la imagen en segundo plano cada vez que cambia la foto
    val imagen by produceState<ImageBitmap?>(initialValue = null, key1 = foto) {
        value = if (foto == null) null else withContext(Dispatchers.IO) { cargarImagen(contexto, foto) }
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(colorFondo),
        contentAlignment = Alignment.Center
    ) {
        val imagenCargada = imagen
        if (imagenCargada != null) {
            Image(
                bitmap = imagenCargada,
                contentDescription = descripcion,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = descripcion,
                tint = colorIcono,
                modifier = Modifier.fillMaxSize(0.6f)
            )
        }
    }
}

private fun cargarImagen(contexto: Context, uri: Uri, ladoMaximo: Int = 1024): ImageBitmap? = runCatching {
    val limites = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    contexto.contentResolver.openInputStream(uri)?.use { flujo ->
        BitmapFactory.decodeStream(flujo, null, limites)
    }
    var reduccion = 1
    while (limites.outWidth / (reduccion * 2) >= ladoMaximo && limites.outHeight / (reduccion * 2) >= ladoMaximo) {
        reduccion *= 2
    }
    val opciones = BitmapFactory.Options().apply { inSampleSize = reduccion }
    contexto.contentResolver.openInputStream(uri)?.use { flujo ->
        BitmapFactory.decodeStream(flujo, null, opciones)
    }?.asImageBitmap()
}.getOrNull()


@Composable
fun DialogoSeleccionFoto(
    fotoActual: Uri?,
    textoInstruccion: String,
    textoBoton: String,
    descripcionAvatar: String,
    onGuardar: (Uri?) -> Unit,
    onCerrar: () -> Unit
) {
    var fotoElegida by remember { mutableStateOf(fotoActual) }
    val selectorFotos = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uriElegida ->
        if (uriElegida != null) fotoElegida = uriElegida
    }

    Dialog(
        onDismissRequest = onCerrar,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(24.dp))
                .background(FotoPanelFondo)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(FotoCirculoFondo)
                    .clickable {
                        selectorFotos.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (fotoElegida != null) {
                    AvatarUsuario(
                        foto = fotoElegida,
                        descripcion = descripcionAvatar,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = FotoSilueta,
                        modifier = Modifier.fillMaxSize(0.8f)
                    )
                    Text(
                        text = textoInstruccion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            BotonPrincipal(
                texto = textoBoton,
                onClick = { onGuardar(fotoElegida) },
                modifier = Modifier.width(160.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarUsuarioPreview() {
    JaveWheelsTheme {
        AvatarUsuario(
            foto = null,
            descripcion = "Avatar",
            modifier = Modifier.size(120.dp)
        )
    }
}