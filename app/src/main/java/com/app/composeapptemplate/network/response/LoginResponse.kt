package com.app.composeapptemplate.network.response

data class LoginResponse(
    var token: String? = null,
    var error: String? = null
)