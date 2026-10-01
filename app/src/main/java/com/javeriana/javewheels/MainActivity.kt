package com.javeriana.javewheels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.javeriana.javewheels.navigation.JWNavHost
import com.javeriana.javewheels.ui.theme.JaveWheelsTheme

// ============================================================
// Única Activity de la app (Single Activity).
// Todas las pantallas son @Composable y las muestra JWNavHost.
// ============================================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JaveWheelsTheme {
                JWNavHost()
            }
        }
    }
}
