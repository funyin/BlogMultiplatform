package org.example.blogmultiplatform.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.core.AppColors
import org.jetbrains.compose.web.css.px

@Composable
fun FooterSection() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(AppColors.Secondary.rgb)
            .height(116.px)
            .padding(24.px),
        contentAlignment = Alignment.Center
    ) {
        SpanText(
            text = "Copyright © 2023 • Funyin Kash",
            modifier = Modifier.fontSize(14.px).color(Colors.White)
        )
    }
}