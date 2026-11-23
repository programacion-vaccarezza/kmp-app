package org.example.project.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.bearerAuth
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkUtils {

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                },
                contentType = ContentType.Any
            )
        }
        defaultRequest {
            Session.token?.let { bearerAuth(it) }
        }
        HttpResponseValidator {
            validateResponse { response ->
                val sentToken = response.call.request.headers[HttpHeaders.Authorization] != null
                val rejected = response.status == HttpStatusCode.Unauthorized ||
                        response.status == HttpStatusCode.Forbidden
                if (sentToken && rejected) {
                    Session.expire()
                }
            }
        }
    }
}
