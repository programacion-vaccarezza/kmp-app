package org.example.project.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(

    val login: String,
    val password: String,
    val firstName: String?,
    val lastName: String?,
    val email: String?

)
