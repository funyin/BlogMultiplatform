package org.example.blogmultiplatform.core.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive


object ApiClient {
    val client = HttpClient(Js) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) { json(Json) }
    }

    suspend inline fun <reified T> get(url: String, parameters: Map<String, String>): T {
        return client.get(url) {
            this.url {
                for (param in parameters) {
                    this.parameters.append(param.key, param.value)
                }
            }
        }.run {
            println(bodyAsText())
            if (this.status.value in HttpStatusCode.OK.value..HttpStatusCode.MultiStatus.value)
                body<T>()
            else
                throw Exception(
                    message = Json.decodeFromString<Map<String, JsonElement>>(bodyAsText())["message"]
                        ?.jsonPrimitive?.content
                )

        }
    }
}