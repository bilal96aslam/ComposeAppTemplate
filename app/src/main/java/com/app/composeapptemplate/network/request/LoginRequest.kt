package com.app.composeapptemplate.network.request

@kotlinx.serialization.Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
