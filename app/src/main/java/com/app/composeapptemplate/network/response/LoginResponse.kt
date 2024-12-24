package com.app.composeapptemplate.network.response

@kotlinx.serialization.Serializable
data class LoginResponse(
    var token: String? = null,
    var error: String? = null
)