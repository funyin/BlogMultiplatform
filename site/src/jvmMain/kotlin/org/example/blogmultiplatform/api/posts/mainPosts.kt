package org.example.blogmultiplatform.api.posts

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.core.errorBody
import org.example.blogmultiplatform.core.successBody
import org.example.blogmultiplatform.data.ApiController
import org.example.blogmultiplatform.data.PostsController.latest
import org.example.blogmultiplatform.data.PostsController.mainPosts
import org.example.blogmultiplatform.data.PostsController.popular
import org.example.blogmultiplatform.data.PostsController.search
import org.example.blogmultiplatform.data.PostsController.sponsoredPosts
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

@Api("latest")
suspend fun latestPosts(context: ApiContext) {
    try {
        val page: Int = context.req.params["page"]?.toInt() ?: throw Exception("page is required")
        val size: Int = context.req.params["size"]?.toInt() ?: throw Exception("size is required")
        val response = context.data.getValue<ApiController>().latest(page = page, size = size)
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Success", data = response)))
        context.res.contentType = "application/json"
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Error", data = e.message.toString())))
    }
}

@Api("sponsored")
suspend fun sponsoredPosts(context: ApiContext) {
    try {
        val response = context.data.getValue<ApiController>().sponsoredPosts()
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Success", data = response)))
        context.res.contentType = "application/json"
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Error", data = e.message.toString())))
    }
}

@Api("popular")
suspend fun popularPosts(context: ApiContext) {
    try {
        val page: Int = context.req.params["page"]?.toInt() ?: throw Exception("page is required")
        val size: Int = context.req.params["size"]?.toInt() ?: throw Exception("size is required")
        val response = context.data.getValue<ApiController>().popular(page = page, size = size)
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Success", data = response)))
        context.res.contentType = "application/json"
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ApiResponse(message = "Error", data = e.message.toString())))
    }
}

@Api("search")
suspend fun search(context: ApiContext) {
    try {
        val page: Int = context.req.params["page"]?.toInt() ?: throw Exception("page is required")
        val size: Int = context.req.params["size"]?.toInt() ?: throw Exception("size is required")
        val category = context.req.params["category"]
        val title = context.req.params["title"]
        val response =
            context.data.getValue<ApiController>().search(title = title, category = category, page = page, size = size)
        context.res.successBody(ApiResponse(message = "Success", data = response))
    } catch (e: Exception) {
        context.res.errorBody(ApiResponse(message = "Error", data = e.message.toString()))
    }
}