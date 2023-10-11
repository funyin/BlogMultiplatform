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
class CreatePosRequest(
    val date: Long,
    val title: String,
    val subtitle: String,
    val thumbnail: String,
    val content: String,
    val category: String,
    val popular: Boolean,
    val main: Boolean,
    val sponsored: Boolean
)

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