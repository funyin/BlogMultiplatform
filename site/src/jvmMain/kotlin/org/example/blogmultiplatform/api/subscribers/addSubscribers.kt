package org.example.blogmultiplatform.api.subscribers

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import org.example.blogmultiplatform.core.errorBody
import org.example.blogmultiplatform.core.successBody
import org.example.blogmultiplatform.data.ApiController
import org.example.blogmultiplatform.data.SubscribersController.addSubscriber
import org.example.blogmultiplatform.models.ApiResponse

@Api("add")
suspend fun addSubscriber(context: ApiContext) {
    try {
        val email = context.req.params["email"] ?: throw Exception("email is required")
        val response = context.data.getValue<ApiController>().addSubscriber(email)
        context.res.successBody(ApiResponse.success(response))
    } catch (e: Exception) {
        context.res.errorBody(ApiResponse(message = e.message.toString(), data = e.message))
    }
}