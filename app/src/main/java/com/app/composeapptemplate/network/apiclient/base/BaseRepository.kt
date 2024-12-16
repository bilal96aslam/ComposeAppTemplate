package com.app.composeapptemplate.network.apiclient.base

import com.app.composeapptemplate.R
import com.app.composeapptemplate.data.ResourceProvider
import com.google.gson.Gson
import kotlinx.serialization.json.JsonObject
import okhttp3.Response
import timber.log.Timber

open class BaseRepository(private val resourceProvider: ResourceProvider) {

    /**
     * Safely executes an API call and handles exceptions.
     */
    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
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
    private fun isNetworkAvailable(): Boolean {
        return NetworkUtils.isNetworkConnected(resourceProvider.getContext())
    }

    /**
     * Processes the API response.
     */
    private fun <T : Any> handleApiResponse(response: Response<T>): ApiResponse<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ApiResponse.Success(data = body, code = response.code())
            } else {
                ApiResponse.Error(
                    ApiError(
                        code = response.code(),
                        message = resourceProvider.getString(R.string.response_error)
                    )
                )
            }
        } else {
            val errorMessage = parseError(response)
            ApiResponse.Error(
                ApiError(code = response.code(), message = errorMessage)
            )
        }
    }

    /**
     * Parses error messages from the response.
     */
    private fun parseError(response: Response<*>): String {
        return try {
            // Parse the error body as a JSON string
            val errorBody = response.errorBody()?.string()

            // If the error body is not null, parse it
            if (!errorBody.isNullOrEmpty()) {
                val jsonObject = Gson().fromJson(errorBody, JsonObject::class.java)
                // Get the "error" field from the response and return its value
                jsonObject.get("error")?.asString
                    ?: resourceProvider.getString(R.string.response_error)
            } else {
                resourceProvider.getString(R.string.response_error)
            }
        } catch (e: Exception) {
            // Log any exceptions and return the default error message
            Timber.e(e, "Error parsing response body: ${e.localizedMessage}")
            resourceProvider.getString(R.string.response_error)
        }
    }
}