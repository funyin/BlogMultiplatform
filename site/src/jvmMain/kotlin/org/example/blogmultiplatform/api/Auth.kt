package org.example.blogmultiplatform.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.data.MongoDB
import org.example.blogmultiplatform.models.LoginRequest

@Api(routeOverride = "login")
suspend fun login(context: ApiContext) {
    try {
        val userRequest = context.req.body?.decodeToString()?.let {
            Json.decodeFromString<LoginRequest>(it)
        }
        val user = userRequest?.let {
            context.data.getValue<MongoDB>().login(it)
        }
        if (user == null) {
            context.res.setBodyText(Json.encodeToString("User doesn't exist"))
            return
        }
        context.res.setBodyText(Json.encodeToString(user))
    } catch (e: Exception) {
        print(e)
        context.res.setBodyText(Json.encodeToString(Exception(e.message)))
    }
}

@Api(routeOverride = "checkUserId")
suspend fun checkUserId(context: ApiContext) {
    try {
        val useId = context.req.body?.decodeToString()?.let {
            Json.decodeFromString<String>(it)
        }
        val result = useId?.let {
            context.data.getValue<MongoDB>().checkUserId(useId)
        }
        result?.let {
            context.res.setBodyText(Json.encodeToString(result))
        } ?: run {
            context.res.setBodyText(Json.encodeToString("asas"))
        }
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(e))
    }
}