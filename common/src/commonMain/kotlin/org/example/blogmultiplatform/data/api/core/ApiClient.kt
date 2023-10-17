package org.example.blogmultiplatform.data.api.core

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive


class ApiClient(private val engine: HttpClientEngine) {
    val client = HttpClient(engine) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) { json(Json) }
    }

    suspend inline fun <reified T> get(url: String, parameters: Map<String, Any> = emptyMap()): T {
        return client.get(url, requestBuilder(parameters)).parseResponse()
    }

    suspend inline fun <reified T> post(url: String, parameters: Map<String, Any> = emptyMap(), body: Any): T {
        return client.post(url, requestBuilder(parameters, body)).parseResponse<T>()
    }

    suspend inline fun <reified T> put(url: String, parameters: Map<String, Any> = emptyMap(), body: Any): T {
        return client.put(url, requestBuilder(parameters, body)).parseResponse<T>()
    }

    fun requestBuilder(
        parameters: Map<String, Any>,
        body: Any? = null
    ): HttpRequestBuilder.() -> Unit = {
        this.url {
            for (param in parameters) {
                this.parameters.append(param.key, param.value.toString())
            }
        }
        body?.let {
            setBody(body)
        }
        this.contentType(ContentType.Application.Json)
    }

    suspend inline fun <reified T> HttpResponse.parseResponse(): T {
        return if (this.status.value in HttpStatusCode.OK.value..HttpStatusCode.MultiStatus.value)
            body<T>()
        else
            throw Exception(
                Json.decodeFromString<Map<String, JsonElement>>(bodyAsText())["message"]
                    ?.jsonPrimitive?.content ?: bodyAsText()
            )
    }
}