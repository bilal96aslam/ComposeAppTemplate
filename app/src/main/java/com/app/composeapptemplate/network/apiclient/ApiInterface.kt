package com.app.composeapptemplate.network.apiclient

import com.app.composeapptemplate.utils.Constant.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

abstract class BaseApiService(
    protected val httpClient: HttpClient,
    protected val baseUrl: String,
) {
    protected suspend inline fun <reified T> get(
        endpoint: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T {
        return httpClient.get(urlString = baseUrl + endpoint, block = block).body()
    }

    protected suspend inline fun <reified T> post(
        endpoint: String,
        block: HttpRequestBuilder.() -> Unit
    ): T {
        return httpClient.post(urlString = baseUrl + endpoint, block = block).body()
    }

    protected suspend inline fun <reified T> put(
        endpoint: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T {
        return httpClient.put(urlString = baseUrl + endpoint, block = block).body()
    }

    protected suspend inline fun <reified T> patch(
        endpoint: String,
        block: HttpRequestBuilder.() -> Unit
    ): T {
        return httpClient.patch(urlString = baseUrl + endpoint, block = block).body()
    }

    protected suspend inline fun <reified T> delete(
        endpoint: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): T {
        return httpClient.delete(urlString = baseUrl + endpoint, block = block).body()
    }
}

class ApiInterface @Inject constructor(client: HttpClient) : BaseApiService(client, BASE_URL) {
    suspend fun login(email: String, password: String): HttpResponse =
        post(endpoint = "login") {
            setBody(
                mapOf(
                    "email" to email,
                    "password" to password
                )
            )
        }
}