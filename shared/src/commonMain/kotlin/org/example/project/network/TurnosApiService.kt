package org.example.project.network

class TurnosApiService(
    private val baseUrl: String = ApiConfig.turnosBaseUrl
) {

    private val httpClient = NetworkUtils.httpClient

}
