package org.example.blogmultiplatform.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.data.ApiController
import org.example.blogmultiplatform.data.PostsController.get
import org.example.blogmultiplatform.data.PostsController.getPosts
import org.example.blogmultiplatform.models.ApiResponse
import org.example.blogmultiplatform.models.Post

@Api
suspend fun posts(context: ApiContext) {
    try {
        val page: Int = context.req.params["page"]?.toInt() ?: 1
        val size: Int = context.req.params["size"]?.toInt() ?: 8
        val search = context.req.params["search"]
        val response = context.data.getValue<ApiController>().getPosts(search, page, size)
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Success", data = response)))
        context.res.contentType = "application/json"
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Error", data = e.message.toString())))
    }
}

@Api(routeOverride = "/post")
suspend fun post(context: ApiContext) {
    try {
        val postId = context.req.params["postId"] ?: throw Exception("postId is required")
        val response: Post = context.data.getValue<ApiController>().get(postId) ?: throw Exception("Post not found")
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Success", data = response)))
        context.res.contentType = "application/json"
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Error", data = e.message.toString())))
    }
}