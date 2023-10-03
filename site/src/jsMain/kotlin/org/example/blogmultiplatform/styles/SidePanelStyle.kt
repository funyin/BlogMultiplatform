package org.example.blogmultiplatform.styles

import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import org.example.blogmultiplatform.core.AppColors
import org.jetbrains.compose.web.css.ms

val NavigationItemStyle by ComponentStyle {
    cssRule(":hover > #svgParent > #vectorIcon") {
        Modifier.styleModifier {
            property("stroke", AppColors.Primary.hex)
        }.transition(CSSTransition(TransitionProperty.All, duration = 300.ms))
    }
    cssRule(" > #navigationText") {
        Modifier
            .transition(CSSTransition(TransitionProperty.All, duration = 300.ms))
            .color(Colors.White)
    }

    cssRule(":hover > #navigationText") {
        Modifier.color(AppColors.Primary.rgb)
    }
}