package com.app.composeapptemplate.network.apiclient

import com.app.composeapptemplate.network.apiclient.base.ApiResponse
import com.app.composeapptemplate.network.response.LoginResponse
import com.app.composeapptemplate.network.response.UserDataResponse

interface ApiHelper {
    suspend fun login(email: String, password: String): ApiResponse<LoginResponse>
    suspend fun getUser(userId: String): ApiResponse<UserDataResponse>
}