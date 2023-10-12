package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.anyLink
import com.varabyte.kobweb.silk.components.style.hover
import com.varabyte.kobweb.silk.components.style.toModifier
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

@Composable
fun CategoryMenuItems(horizontal: Boolean) {
    val categories = Category.entries
    categories.forEachIndexed { index, it ->
        MenuItem(
            category = it,
            modifier = Modifier.margin(
                right = if (horizontal && index != categories.lastIndex) 24.px else 0.px,
                bottom = if (!horizontal && index != categories.lastIndex) 24.px else 0.px
            )
        )
    }
}

val MenuItemStyle by ComponentStyle {
    base {
        Modifier.color(Colors.White)
            .transition(CSSTransition(TransitionProperty.of("color"), duration = 300.ms))
    }
    anyLink {
        Modifier.color(Colors.White)
    }
    hover {
        Modifier.color(AppColors.Primary.rgb)
    }
}

@Composable
private fun MenuItem(modifier: Modifier = Modifier, category: Category) {
    Link(
        modifier = MenuItemStyle.toModifier().fontFamily(Res.Strings.FONT_FAMILY).fontSize(16.px).then(modifier)
            .textDecorationLine(
                TextDecorationLine.None
            ).onClick {

            }, path = category.name
    )
}