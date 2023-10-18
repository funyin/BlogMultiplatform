package org.example.blogmultiplatform.models

import kotlinx.serialization.Serializable

@Serializable
actual enum class Category {
    Technology,
    Programming,
    Design
}