package org.example.blogmultiplatform.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val message: String,
    val data: T
) {
    companion object {
        fun <T> success(data: T) = ApiResponse(message = "Success", data)
        fun <T> error(data: T) = ApiResponse(message = "Error", data)
    }
}