package org.example.project.network

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import org.example.project.network.model.LoginRequest
import org.example.project.network.model.RegisterRequest

class CatalogoApiService(
    private val baseUrl: String = ApiConfig.catalogoBaseUrl
) {

    private val httpClient = NetworkUtils.httpClient

    suspend fun register(request: RegisterRequest): HttpStatusCode {
        return httpClient.post("$baseUrl/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.status
    }

    suspend fun login(request: LoginRequest): HttpResponse {
        return httpClient.post("$baseUrl/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

}
