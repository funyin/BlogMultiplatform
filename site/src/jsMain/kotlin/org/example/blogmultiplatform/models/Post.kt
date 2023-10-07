package org.example.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class Post(
    @SerialName("_id")
    actual val id: String,
    actual val date: Long,
    actual val title: String,
    actual val subtitle: String,
    actual val thumbnail: String,
    actual val content: String,
    actual val category: String,
    actual val popular: Boolean,
    actual val main: Boolean,
    actual val sponsored: Boolean
)
