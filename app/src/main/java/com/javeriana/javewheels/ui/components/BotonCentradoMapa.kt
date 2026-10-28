package com.javeriana.javewheels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.javeriana.javewheels.R

@Composable
fun BoxScope.BotonCentradoMapa(
    onCentrar: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onCentrar,
        modifier = modifier
            .align(Alignment.CenterEnd)
            .padding(end = 20.dp, bottom = 40.dp)
            .size(46.dp)
            .shadow(4.dp, CircleShape)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Icon(
            imageVector = Icons.Default.MyLocation,
            contentDescription = stringResource(R.string.ui_centrar),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp)
        )
    }
}
