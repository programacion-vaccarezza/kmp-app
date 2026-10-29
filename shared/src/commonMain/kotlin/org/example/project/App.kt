package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import org.example.project.screen.LoginScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(
            screen = LoginScreen()
        )
    }
}
