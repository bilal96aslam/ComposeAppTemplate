package com.app.composeapptemplate.network.apiclient

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Inject
import kotlinx.serialization.json.Json
import timber.log.Timber

class KtorHttpClient @Inject constructor() {

    fun getHttpClient() = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.tag(TAG_KTOR_LOGGING).i(message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Timber.tag(TAG_HTTP_STATUS_LOGGING).i("${response.status.value}")
            }
        }

        install(DefaultRequest) {
            /** you can also define other parameters in this like
             * parameter("api_key", "api_value")
            **/
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
        }

        engine {
            connectTimeout = TIMEOUT
            socketTimeout = TIMEOUT
        }
    }

    companion object {
        private const val TIMEOUT = 10_000
        private const val TAG_KTOR_LOGGING = "ktor_logging"
        private const val TAG_HTTP_STATUS_LOGGING = "http_status:"
    }
}