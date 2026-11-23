package org.example.project.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import org.example.project.network.CatalogoApiService
import org.example.project.network.Session
import org.example.project.network.model.LoginRequest
import org.example.project.network.model.LoginResponse

class LoginScreen : Screen {

    @Composable
    override fun Content() {

        val navigator: Navigator = LocalNavigator.currentOrThrow

        var login by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        val scope = rememberCoroutineScope()
        val apiService = remember { CatalogoApiService() }
        var isLoading by remember { mutableStateOf(false) }
        var errorMessage by remember {
            mutableStateOf(if (Session.expired) "Tu sesión venció, volvé a iniciar sesión" else null)
        }

        fun login() {
            scope.launch {
                isLoading = true
                errorMessage = null
                try {
                    val response = apiService.login(
                        LoginRequest(
                            login = login,
                            password = password
                        )
                    )
                    when (response.status) {
                        HttpStatusCode.OK -> {
                            Session.token = response.body<LoginResponse>().token
                            Session.login = login
                            Session.expired = false
                            navigator.replaceAll(MainScreen())
                        }
                        HttpStatusCode.Unauthorized -> errorMessage = "Usuario o contraseña incorrectos"
                        HttpStatusCode.BadRequest -> errorMessage = "Completá el usuario y la contraseña"
                        else -> errorMessage = "No se pudo iniciar sesión"
                    }
                } catch (e: Exception) {
                    errorMessage = "No se pudo conectar con el servidor"
                } finally {
                    isLoading = false
                }
            }
        }

        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { login() },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isLoading) "Ingresando..." else "Ingresar"
                )
            }
            errorMessage?.let {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }
            TextButton(
                onClick = {
                    navigator.push(RegisterScreen())
                }
            ) {
                Text(
                    text = "Crear cuenta"
                )
            }
        }
    }
}
