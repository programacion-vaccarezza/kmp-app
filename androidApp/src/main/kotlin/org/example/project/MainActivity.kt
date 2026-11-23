package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                catalogoBaseUrl = BuildConfig.CATALOGO_BASE_URL,
                turnosBaseUrl = BuildConfig.TURNOS_BASE_URL
            )
        }
    }
}
