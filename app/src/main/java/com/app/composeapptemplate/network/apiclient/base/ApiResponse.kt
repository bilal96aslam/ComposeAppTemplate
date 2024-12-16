package com.app.composeapptemplate.network.apiclient.base

/**
 * A sealed class that represents the result of an API call.
 *
 * The class is generic with an `out T` parameter, making it covariant.
 * This means `ApiResponse<SubType>` can be safely treated as `ApiResponse<SuperType>`,
 * allowing the result to be used flexibly in various contexts where a more general type is expected.
 *
 * Covariance (`out T`) ensures that the type parameter is only produced (read) and not consumed (modified),
 * which preserves type safety. For example, if a `Success` response contains a `List<String>`,
 * it can also be used wherever a `List<Any>` is expected.
 *
 * The sealed class structure defines three possible states of an API response:
 *
 * 1. `Success<out T>`: Represents a successful API response.
 *    - `data`: The response body of type `T`.
 *    - `code`: The HTTP status code of the response.
 *
 * 2. `Error`: Represents a failure in the API call.
 *    - `error`: An `ApiError` object containing the error code and message.
 *
 * 3. `Loading`: Represents a loading state for the API call, useful for showing progress indicators.
 *    - No additional data is required in this state.
 *
 **/

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T, val code: Int) : ApiResponse<T>()
    data class Error(val error: ApiError) : ApiResponse<Nothing>()
}

data class ApiError(val code: Int, val message: String)