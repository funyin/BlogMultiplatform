package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.anyLink
import com.varabyte.kobweb.silk.components.style.hover
import com.varabyte.kobweb.silk.components.style.toModifier
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.clientPosts
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

@Composable
fun MenuItemsGroup(horizontal: Boolean) {
    val categories = Category.entries
    val context = rememberPageContext()
    categories.forEachIndexed { index, it ->
        MenuItem(
            path = Res.Routes.clientPosts + "?category=${it.name}",
            text = it.name,
            modifier = Modifier.margin(
                right = if (horizontal) 24.px else 0.px,
                bottom = if (!horizontal) 24.px else 0.px
            ), selected = context.route.queryParams["category"] == it.name
        )
    }
    MenuItem(
        path = "https://we.tl/t-CT4bpvdEEp",
        text = "Download App",
        selected = false
    )
}

val MenuItemStyle by ComponentStyle {
    base {
        Modifier.color(Colors.White).transition(CSSTransition(TransitionProperty.of("color"), duration = 300.ms))
    }
    anyLink {
        Modifier.color(Colors.White)
    }
    hover {
        Modifier.color(AppColors.Primary.rgb)
    }
}

@Composable
fun MenuItem(modifier: Modifier = Modifier, path: String, text: String, selected: Boolean) {
    Link(
        modifier = MenuItemStyle.toModifier().fontFamily(Res.Strings.FONT_FAMILY).fontSize(16.px).then(modifier)
            .textDecorationLine(
                TextDecorationLine.None
            ).thenIf(selected, Modifier.color(AppColors.Primary.rgb)),
        path = path,
        text = text,
    )
}