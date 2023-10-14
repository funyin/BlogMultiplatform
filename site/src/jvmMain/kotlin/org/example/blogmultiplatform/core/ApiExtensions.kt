package org.example.blogmultiplatform.core

import com.varabyte.kobweb.api.http.Response
import com.varabyte.kobweb.api.http.setBodyText
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> Response.successBody(body: T) {
    contentType = "application/json"
    setBodyText(Json.encodeToString(body))
}

inline fun <reified T> Response.errorBody(body: T) {
    contentType = "application/json"
    setBodyText(Json.encodeToString(body))
    status = HttpStatusCode.BadRequest.value
}