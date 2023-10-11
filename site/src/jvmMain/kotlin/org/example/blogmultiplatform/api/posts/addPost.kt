package org.example.blogmultiplatform.api.posts

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.readBodyText
import com.varabyte.kobweb.api.http.setBodyText
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.data.ApiController
import org.example.blogmultiplatform.data.PostsController.addPost
import org.example.blogmultiplatform.data.PostsController.updatePost
import org.example.blogmultiplatform.models.ApiResponse
import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.UpdatePostRequest

@Api
suspend fun addPost(context: ApiContext) {
    try {
        val post = context.req.body?.decodeToString()?.let {
            Json.decodeFromString<Post>(it)
        }
        if (post == null) {
            context.res.setBodyText("Post is required")
            return
        }
        val response = context.data.getValue<ApiController>().addPost(post)
        context.res.setBodyText(Json.encodeToString(response))
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(e.message))
    }
}

@Api("update")
suspend fun updatePost(context: ApiContext) {
    try {
        val post = context.req.readBodyText()?.let {
            Json.decodeFromString<UpdatePostRequest>(it)
        } ?: throw Exception("Post content is required")

        val response = context.data.getValue<ApiController>().updatePost(post)
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Success", data = response)))
        context.res.contentType = "application/json"
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ApiResponse.error(e.message)))
        context.res.status = HttpStatusCode.BadRequest.value
    }
}