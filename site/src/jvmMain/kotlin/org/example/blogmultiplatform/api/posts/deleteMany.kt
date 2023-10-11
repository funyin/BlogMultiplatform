package org.example.blogmultiplatform.api.posts

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.readBodyText
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.data.ApiController
import org.example.blogmultiplatform.data.PostsController.deletePosts
import org.example.blogmultiplatform.models.ApiResponse
import org.example.blogmultiplatform.models.DeletePostsRequest

@Api
suspend fun deleteMany(context: ApiContext) {
    try {
        val items = context.req.readBodyText()?.let { Json.decodeFromString<DeletePostsRequest>(it).items }
            ?: throw IllegalArgumentException("items is required")
        val response = context.data.getValue<ApiController>().deletePosts(items)
        context.res.setBodyText(
            Json.encodeToString(
                ApiResponse(
                    message = "Success",
                    data = response
                )
            )
        )
        context.res.contentType = "application/json"
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Error", data = e.message.toString())))
    }
}