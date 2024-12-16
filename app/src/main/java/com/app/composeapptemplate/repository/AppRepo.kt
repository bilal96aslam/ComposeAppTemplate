package com.app.composeapptemplate.repository

import com.app.composeapptemplate.data.ResourceProvider
import com.app.composeapptemplate.network.apiclient.base.BaseRepository
import javax.inject.Inject

class AppRepo @Inject constructor(
    resourceProvider: ResourceProvider,
) : BaseRepository(resourceProvider) {

//    override suspend fun login(email: String, password: String): ApiResponse<LoginResponse> {
//        return safeApiCall { apiService.login(email, password) }
//    }
}