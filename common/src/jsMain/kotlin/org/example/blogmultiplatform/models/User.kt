package org.example.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class User(
    @SerialName(value = "_id")
    actual val id: String,
    actual val userName: String,
)