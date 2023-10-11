package org.example.blogmultiplatform.api.posts

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.data.ApiController
import org.example.blogmultiplatform.data.PostsController.addPost
import org.example.blogmultiplatform.models.Post

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