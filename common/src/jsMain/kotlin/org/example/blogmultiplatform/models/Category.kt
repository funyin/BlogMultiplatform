package org.example.blogmultiplatform.models

import kotlinx.serialization.Serializable
import org.example.blogmultiplatform.core.AppColors
@Serializable
actual enum class Category(val color: AppColors) {
    Technology(AppColors.Green),
    Programming(AppColors.Yellow),
    Design(AppColors.Purple)
}