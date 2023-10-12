package org.example.blogmultiplatform.api.posts

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.data.ApiController
import org.example.blogmultiplatform.data.PostsController.mainPosts
import org.example.blogmultiplatform.models.ApiResponse

@Api("main")
suspend fun mainPosts(context: ApiContext) {
    try {
        val response = context.data.getValue<ApiController>().mainPosts()
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Success", data = response)))
        context.res.contentType = "application/json"
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Error", data = e.message.toString())))
    }
}