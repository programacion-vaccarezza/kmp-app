package org.example.project.network

class CatalogoApiService(
    private val baseUrl: String = ApiConfig.catalogoBaseUrl
) {

    private val httpClient = NetworkUtils.httpClient

}
