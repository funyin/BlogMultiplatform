package org.example.blogmultiplatform.data.repository

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.models.UiState


@OptIn(InternalAPI::class)
class ThirdPartyRepository(private val apiClient: ApiClient) {
//    suspend fun uploadFile(file: String, fileName: String): UiState<String> {
//        return try {
//
//            val response = apiClient.client.submitFormWithBinaryData(
//                url = URLBuilder("https://www.filestackapi.com/api/store/S3").apply {
//                    parameters.append("key", "AEiFyphYjT9SUy59pJc7bz")
//                }.buildString(),
//                formData = formData {
//                    append("data", file, Headers.build {
//                        append(HttpHeaders.ContentType, "image/png")
//                        append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
//                    })
//                }
//            )
//            val success = response.body<Map<String, Any>>()["url"].toString()
//            UiState.Success(success)
//        } catch (e: Exception) {
//            UiState.Error(errorMessage = e.message.toString())
//        }
//    }


    suspend fun uploadFile(file: String, fileName: String): UiState<String> {
        return try {
            val response = apiClient
                .client
                .post(url = Url("https://www.filestackapi.com/api/store/S3?key=AEiFyphYjT9SUy59pJc7bz")) {
                    headers {
                        append(HttpHeaders.ContentType, "image/png")
                    }
                    setBody(file.toByteArray())
                }

            if (response.status.isSuccess()) {
                val responseText = response.bodyAsText()
                val json = Json { ignoreUnknownKeys = true }
                val responseData = json.parseToJsonElement(responseText).jsonObject
                val success = responseData["url"]?.jsonPrimitive?.content ?: ""
                UiState.Success(success)
            } else {
                UiState.Error(errorMessage = "File upload failed with status code: ${response.status}")
            }
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message.toString())
        }
    }


}