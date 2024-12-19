package com.app.composeapptemplate.network.apiclient.base

import com.app.composeapptemplate.R
import com.app.composeapptemplate.utils.ResourceProvider
import com.app.composeapptemplate.utils.NetworkUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import timber.log.Timber

open class BaseRepository(val resourceProvider: ResourceProvider) {

    /*
     * Safely executes an API call and handles exceptions.
     */
    suspend inline fun <reified T : Any> safeApiCall(apiCall: () -> HttpResponse): ApiResponse<T> {
        return try {
            if (!isNetworkAvailable()) {
                ApiError(
                    code = -1,
                    message = resourceProvider.getString(R.string.internet_error)
                )
            }

            // Execute the API call
            val response = apiCall()

            // Handle the response
            handleApiResponse(response)
        } catch (e: Exception) {
            Timber.e(e, "Error parsing response body: ${e.localizedMessage}")
            ApiResponse.Error(ApiError(code = -1, message = e.localizedMessage ?: "Unknown error"))
        }
    }

    /**
     * Checks for network availability.
     */
    fun isNetworkAvailable(): Boolean {
        return NetworkUtils.isNetworkConnected(resourceProvider.getContext())
    }

    /**
     * Processes the API response.
     */
    suspend inline fun <reified T : Any> handleApiResponse(response: HttpResponse): ApiResponse<T> {
        return if (response.status.isSuccess()) {
            try {
                val body: T = response.body()
                ApiResponse.Success(data = body, code = response.status.value)
            } catch (e: Exception) {
                Timber.e(e, "Error parsing response body: ${e.localizedMessage}")
                ApiResponse.Error(
                    ApiError(
                        code = response.status.value,
                        message = resourceProvider.getString(R.string.response_error)
                    )
                )
            }
        } else {
            val errorMessage = parseError(response)
            ApiResponse.Error(
                ApiError(code = response.status.value, message = errorMessage)
            )
        }
    }

    /**
     * Parses error messages from the response.
     */
    suspend fun parseError(response: HttpResponse): String {
        return try {
            val errorBody = response.body<String>()
            if (errorBody.isNotEmpty()) {
                // Parse the error body as a JSON string
                val jsonObject = Gson().fromJson(errorBody, JsonObject::class.java)
                // Get the "error" field from the response and return its value
                jsonObject.get("error")?.asString
                    ?: resourceProvider.getString(R.string.response_error)
            } else {
                resourceProvider.getString(R.string.response_error)
            }
        } catch (e: Exception) {
            Timber.e(e, "Error parsing error body: ${e.localizedMessage}")
            resourceProvider.getString(R.string.response_error)
        }
    }
}