package org.example.project.network

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Session {

    var token: String? = null
    var login: String? = null
    var expired by mutableStateOf(false)

    fun expire() {
        token = null
        login = null
        expired = true
    }
}
