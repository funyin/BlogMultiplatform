package org.example.blogmultiplatform.models

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb

enum class Theme(
    val hex: String,
    val rgb: CSSColorValue,
) {
    Primary(hex = "#00A2ff", rgb = rgb(0, 166, 255)),
    LightGrey(hex = "#FAFAFA", rgb = rgb(250, 250, 250))
}