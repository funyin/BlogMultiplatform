package org.example.blogmultiplatform.modules.posts

import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.api.PostApi
import org.example.blogmultiplatform.core.network.ApiClient
import org.example.blogmultiplatform.models.CreatePosRequest
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.apiBaseUrl

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

    override suspend fun posts(page: Int, size: Int): List<PostLight> {
        return ApiClient.get<List<PostLight>>(
            "${Res.Strings.apiBaseUrl}posts",
            parameters = mapOf("page" to page, "size" to size)
        )
    }
}