package org.example.blogmultiplatform.modules.auth

import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.models.LoginRequest
import org.example.blogmultiplatform.models.User

object AuthApi {
    private val api = window.api
    suspend fun login(request: LoginRequest): User? {
        return try {
            val response = api.tryPost(apiPath = "/auth/login", body = Json.encodeToString(request).encodeToByteArray())
            response?.decodeToString()?.let { Json.decodeFromString<User>(it) }
        } catch (e: Exception) {
            println(e)
            null
        }
    }

    suspend fun checkUserId(userId: String): Boolean {
        return try {
            val response = api.tryPost(apiPath = "/auth/checkUserId", body = Json.encodeToString(userId).encodeToByteArray())
            response?.decodeToString()?.let {
                Json.decodeFromString<Boolean>(it)
            } ?: false
        } catch (e: SerializationException) {
            false
        }
    }
}