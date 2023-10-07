package org.example.blogmultiplatform.modules.posts

import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.api.PostApi
import org.example.blogmultiplatform.models.CreatePosRequest

object PostApiImpl : PostApi {
    private val api = window.api
    override suspend fun createPost(post: CreatePosRequest): Boolean {
        return try {
            val response = api.tryPost(apiPath = "/posts/addpost", body = Json.encodeToString(post).encodeToByteArray())
            response?.decodeToString().toString().toBoolean()
        } catch (e: Exception) {
            false
        }
    }
}