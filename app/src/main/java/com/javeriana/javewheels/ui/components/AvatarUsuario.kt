package com.javeriana.javewheels.ui.components

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private data class EstadoFoto(val uri: Uri?, val imagen: ImageBitmap? = null, val cargando: Boolean = false)

@Composable
fun AvatarUsuario(
    modifier: Modifier = Modifier,
    foto: Uri? = null,
    descripcion: String? = null,
    colorFondo: Color = MaterialTheme.colorScheme.primaryContainer,
    colorIcono: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    tamano: Dp = 44.dp,
    alCargar: (Boolean?) -> Unit = {}
) {
    val contexto = LocalContext.current
    val estado by produceState(EstadoFoto(foto, cargando = foto != null), foto) {
        value = EstadoFoto(foto, cargando = foto != null)
        val imagen = foto?.let { withContext(Dispatchers.IO) { cargarImagen(contexto, it) } }
        value = EstadoFoto(foto, imagen)
    }
    val cargando = foto != null && (estado.uri != foto || estado.cargando)
    val imagen = estado.imagen.takeIf { estado.uri == foto }
    val error = foto != null && !cargando && imagen == null
    LaunchedEffect(foto, cargando, imagen) { alCargar(if (cargando) null else foto == null || imagen != null) }
    val descripcionEstado = when {
        cargando -> stringResource(R.string.foto_cargando)
        error -> stringResource(R.string.foto_error)
        else -> descripcion
    }
    Box(
        modifier.size(tamano).clip(CircleShape).background(colorFondo),
        contentAlignment = Alignment.Center
    ) {
        when {
            cargando -> CircularProgressIndicator(
                Modifier.fillMaxSize(0.4f).semantics { contentDescription = descripcionEstado.orEmpty() },
                strokeWidth = 2.dp
            )
            imagen != null -> Image(imagen, descripcion, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            else -> Icon(
                if (error) Icons.Default.BrokenImage else Icons.Default.Person,
                descripcionEstado, Modifier.fillMaxSize(0.6f), tint = colorIcono
            )
        }
    }
}

private fun cargarImagen(contexto: Context, uri: Uri): ImageBitmap? = try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        // ImageDecoder respeta la orientación de las fotos y limita también las panorámicas.
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(contexto.contentResolver, uri)) { decoder, info, _ ->
            val mayorLado = maxOf(info.size.width, info.size.height)
            if (mayorLado > 1024) {
                val escala = 1024f / mayorLado
                decoder.setTargetSize((info.size.width * escala).toInt().coerceAtLeast(1),
                    (info.size.height * escala).toInt().coerceAtLeast(1))
            }
        }.asImageBitmap()
    } else {
        val limites = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        contexto.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, limites) }
        var reduccion = 1
        while (maxOf(limites.outWidth, limites.outHeight) / reduccion > 1024) reduccion *= 2
        val opciones = BitmapFactory.Options().apply { inSampleSize = reduccion }
        contexto.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, opciones) }?.asImageBitmap()
    }
} catch (_: IOException) {
    null
} catch (_: SecurityException) {
    null
} catch (_: IllegalArgumentException) {
    null
}

@Composable
fun DialogoSeleccionFoto(
    fotoActual: Uri?,
    textoInstruccion: String,
    textoBoton: String,
    descripcionAvatar: String,
    onGuardar: (Uri?) -> Unit,
    onCerrar: () -> Unit
) {
    var fotoElegida by rememberSaveable { mutableStateOf(fotoActual) }
    var fotoValida by remember { mutableStateOf<Boolean?>(if (fotoActual == null) true else null) }
    val selector = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) { fotoValida = null; fotoElegida = uri }
    }
    AlertDialog(
        onDismissRequest = onCerrar,
        title = { Text(descripcionAvatar) },
        text = {
            Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AvatarUsuario(foto = fotoElegida, descripcion = descripcionAvatar,
                    modifier = Modifier.size(144.dp), alCargar = { fotoValida = it })
                Text(if (fotoElegida == null) stringResource(R.string.foto_sin_seleccionar) else textoInstruccion)
                if (fotoElegida != null && fotoValida == false) Text(stringResource(R.string.foto_error),
                    color = MaterialTheme.colorScheme.error)
                OutlinedButton(onClick = {
                    selector.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }, modifier = Modifier.fillMaxWidth()) { Text(stringResource(R.string.foto_elegir)) }
                if (fotoElegida != null) TextButton(onClick = { fotoElegida = null; fotoValida = true }) {
                    Text(stringResource(R.string.foto_quitar))
                }
            }
        },
        confirmButton = { TextButton(enabled = fotoValida == true, onClick = { onGuardar(fotoElegida) }) { Text(textoBoton) } },
        dismissButton = { TextButton(onClick = onCerrar) { Text(stringResource(R.string.cancelar)) } }
    )
}

@Preview(showBackground = true)
@Composable
private fun AvatarUsuarioPreview() {
    JaveWheelsTheme { AvatarUsuario(modifier = Modifier.size(120.dp)) }
}
