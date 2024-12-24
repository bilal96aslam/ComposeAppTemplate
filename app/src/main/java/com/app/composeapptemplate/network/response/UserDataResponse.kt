package com.app.composeapptemplate.network.response

import kotlinx.serialization.SerialName

//@kotlinx.serialization.Serializable
//data class UserDataResponse(
//    @SerialName("id")
//    val id: Int,
//    @SerialName("email")
//    val email: String,
//    @SerialName("first_name")
//    val firstName: String,
//    @SerialName("last_name")
//    val lastName: String,
//    @SerialName("avatar")
//    val avatar: String
//)

@kotlinx.serialization.Serializable
data class UserDataResponse(
    val data: UserData?,
    val support: Support
)

@kotlinx.serialization.Serializable
data class UserData(
    val id: Int,
    val email: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val avatar: String
)

@kotlinx.serialization.Serializable
data class Support(
    val url: String,
    val text: String
)
