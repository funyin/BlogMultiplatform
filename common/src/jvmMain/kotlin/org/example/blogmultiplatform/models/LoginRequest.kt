package org.example.blogmultiplatform.models

import kotlinx.serialization.Serializable

@Serializable
actual data class LoginRequest(
    actual val userName: String,
    actual val password: String,
)