package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.example.project.network.ApiConfig
import org.example.project.screen.LoginScreen

@Composable
fun App(
    catalogoBaseUrl: String,
    turnosBaseUrl: String
) {
    ApiConfig.catalogoBaseUrl = catalogoBaseUrl
    ApiConfig.turnosBaseUrl = turnosBaseUrl

    MaterialTheme {
        Navigator(
            screen = LoginScreen()
        )
    }
}
