package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.icons.fa.FaSpinner
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.style.*
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

val AppButtonStyle by ComponentStyle {
    base {
        Modifier.width(350.px)
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
    }
}

val AppButtonRoundedVariant by AppButtonStyle.addVariant {
    base {
        Modifier.borderRadius(35.px)
    }
}

val AppButtonTextVariant by AppButtonStyle.addVariant {
    base {
        Modifier
            .backgroundColor(Colors.Transparent)
            .color(AppColors.Primary.rgb)
            .transition(
                CSSTransition(TransitionProperty.of("background"), 200.ms),
                CSSTransition(TransitionProperty.of("color"), 210.ms),
            )
    }
    hover {
        Modifier.background(AppColors.Primary.rgb)
            .color(Colors.White)
    }
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    loading: Boolean = false,
    variant: ComponentVariant = ComponentVariant.Empty,
    onClick: () -> Unit = {}
) {
    Button(
        attrs = AppButtonStyle.toModifier(variant)
            .onClick { onClick() }
            .then(modifier)
            .toAttrs()
    ) {
        if (loading)
            FaSpinner(size = IconSize.SM, modifier = Modifier.color(Colors.White))
        else
            SpanText(text = text)
    }
}