package org.example.blogmultiplatform.core

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba

enum class AppColors(
    val hex: String,
    val rgb: CSSColorValue,
) {
    Primary(hex = "#00A2ff", rgb = rgb(0, 166, 255)),
    LightGrey(hex = "#FAFAFA", rgb = rgb(250, 250, 250)),
    Secondary(hex = "#001019", rgb = rgb(0, 16, 25)),
    HalfWhite(hex = "#ffffff", rgb = rgba(255, 255, 255, a = .5)),
    HalfBlack(hex = "#000000", rgb = rgba(0, 0, 0, a = .5)),
    Green(hex = "#00FF94", rgb = rgb(r = 0, g = 255, b = 148)),
    Yellow(hex = "#FFEC45", rgb = rgb(r = 255, g = 236, b = 69)),
    Red(hex = "#FF6359", rgb = rgb(r = 255, g = 99, b = 89)),
    Purple(hex = "#8B6DFF", rgb = rgb(r = 139, g = 109, b = 255)),
    Blue(hex = "#3300FF", rgb = rgb(r = 51, g = 0, b = 255)),
    Tertiary(hex = "#001925", rgb = rgb(r = 0, g = 25, b = 37))
}