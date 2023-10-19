package org.example.blogmultiplatform.api.auth

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.core.errorBody
import org.example.blogmultiplatform.core.successBody
import org.example.blogmultiplatform.data.ApiController
import org.example.blogmultiplatform.data.AuthController.checkUserId
import org.example.blogmultiplatform.data.AuthController.login
import org.example.blogmultiplatform.models.LoginRequest

@Api(routeOverride = "login")
suspend fun login(context: ApiContext) {
    try {
        val userRequest = context.req.body?.decodeToString()?.let {
            Json.decodeFromString<LoginRequest>(it)
        }
        val user = userRequest?.let {
            context.data.getValue<ApiController>().login(it)
        }
        if (user == null) {
            context.res.successBody("User doesn't exist")
            return
        }
        context.res.successBody(user)
    } catch (e: Exception) {
        print(e)
        context.logger.error(e.toString())
        context.res.errorBody(e.message)
    }
}

@Api(routeOverride = "checkUserId")
suspend fun checkUserId(context: ApiContext) {
    try {
        val useId = context.req.body?.decodeToString()?.let {
            Json.decodeFromString<String>(it)
        }
        val result = useId?.let {
            context.data.getValue<ApiController>().checkUserId(useId)
        }
        result?.let {
            context.res.setBodyText(Json.encodeToString(result))
        } ?: run {
            context.res.setBodyText(Json.encodeToString("User does not exist"))
        }
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(e))
    }
}