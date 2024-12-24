package com.app.composeapptemplate.repository

import com.app.composeapptemplate.network.apiclient.ApiHelper
import com.app.composeapptemplate.network.apiclient.ApiInterface
import com.app.composeapptemplate.network.apiclient.base.ApiResponse
import com.app.composeapptemplate.network.apiclient.base.BaseRepository
import com.app.composeapptemplate.network.response.LoginResponse
import com.app.composeapptemplate.network.response.UserDataResponse
import com.app.composeapptemplate.utils.ResourceProvider
import javax.inject.Inject

class AppRepo @Inject constructor(
    resourceProvider: ResourceProvider,
    private val apiInterface: ApiInterface
) : BaseRepository(resourceProvider), ApiHelper {

    override suspend fun login(email: String, password: String): ApiResponse<LoginResponse> {
        return safeApiCall {
            apiInterface.login(email, password)
        }
    }

    override suspend fun getUser(userId: String): ApiResponse<UserDataResponse> {
        return safeApiCall {
            apiInterface.getUser(userId)
        }
    }
}