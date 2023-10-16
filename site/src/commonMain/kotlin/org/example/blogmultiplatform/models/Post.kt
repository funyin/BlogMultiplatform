package org.example.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

expect class Post {
    val id: String
    val date: Long
    val title: String
    val subtitle: String
    val thumbnail: String
    val content: String
    val category: String
    val popular: Boolean
    val main: Boolean
    val sponsored: Boolean
}

@Serializable
open class CreatePostRequest(
    open val date: Long,
    open val title: String,
    open val subtitle: String,
    open var thumbnail: String,
    open val content: String,
    open val category: String,
    open val popular: Boolean,
    open val main: Boolean,
    open val sponsored: Boolean
)

@Serializable
expect class UpdatePostRequest {
    @SerialName("_id")
    val id: String
    val date: Long
    val title: String
    val subtitle: String
    var thumbnail: String
    val content: String
    val category: String
    val popular: Boolean
    val main: Boolean
    val sponsored: Boolean

    companion object{
        fun from(request: CreatePostRequest,postId:String): UpdatePostRequest
    }
}

@Serializable
expect class PostLight {
    @SerialName("_id")
    val id: String
    val date: Long
    val title: String
    val subtitle: String
    val thumbnail: String
    val category: String
}

@Serializable
data class DeletePostsRequest(val items: List<String>)