package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

@Composable
fun AppButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        attrs = Modifier
            .width(350.px)
            .height(54.px)
            .backgroundColor(AppColors.Primary.rgb)
            .color(Colors.White)
            .border(
                width = 0.px,
                style = LineStyle.None,
                color = Colors.Transparent
            ).outline(
                width = 0.px,
                style = LineStyle.None,
                color = Colors.Transparent
            )
            .borderRadius(4.px)
            .fontWeight(FontWeight.Medium)
            .fontFamily(Res.Strings.FONT_FAMILY)
            .fontSize(14.px)
            .cursor(Cursor.Pointer)
            .onClick { onClick() }
            .then(modifier)
            .toAttrs()
    ) {
        SpanText(text = text)
    }
}