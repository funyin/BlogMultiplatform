package org.example.blogmultiplatform.components.sections.panels

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.components.widgets.VectorIcon
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.core.SessionManager
import org.example.blogmultiplatform.res.*
import org.example.blogmultiplatform.styles.NavigationItemStyle
import org.jetbrains.compose.web.css.px

@Composable
fun SidePanel(modifier: Modifier) {
    Column(
        modifier = Modifier.padding(topBottom = 40.px, leftRight = 50.px)
            .width(Res.Dimens.SIDE_PANEL_WIDTH.px)
            .fillMaxHeight()
            .background(AppColors.Secondary.rgb)
            .then(modifier)
    ) {
        Image(Res.Images.logo, desc = "Logo", modifier = Modifier.margin(bottom = 60.px))
        SpanText(
            "Dashboard",
            modifier = Modifier
                .fontFamily(Res.Strings.FONT_FAMILY)
                .fontSize(14.px)
                .color(AppColors.HalfWhite.rgb)
                .margin(bottom = 30.px),
        )
        NavigationItems()
    }
}

@Composable
fun NavigationItems() {
    val context = rememberPageContext()
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "Home",
        icon = Res.PathIcon.home,
        selected = context.route.path.endsWith(Res.Routes.adminHome)
    ) {
        context.router.navigateTo(Res.Routes.adminHome)
    }
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "Create Post",
        icon = Res.PathIcon.create,
        selected = context.route.path.endsWith(Res.Routes.createPost)
    ) {
        context.router.navigateTo(Res.Routes.createPost)
    }
    NavigationItem(
        modifier = Modifier.margin(bottom = 24.px),
        title = "My Posts",
        icon = Res.PathIcon.posts,
        selected = context.route.path.endsWith(Res.Routes.myPosts)
    ) {
        context.router.navigateTo(Res.Routes.myPosts)
    }
    NavigationItem(
        title = "Logout",
        icon = Res.PathIcon.logout
    ) {
        SessionManager.endSession()
        context.router.navigateTo(Res.Routes.login)
    }
}

@Composable
fun NavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    title: String,
    icon: String,
    onClick: () -> Unit
) {
    val iconColor = if (selected) AppColors.Primary.hex else AppColors.HalfWhite.hex
    val contentColor = if (selected) AppColors.Primary.rgb else Colors.White
    Row(
        modifier = NavigationItemStyle.toModifier()
            .then(modifier)
            .cursor(Cursor.Pointer)
            .onClick {
                onClick()
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        VectorIcon(
            modifier = Modifier.margin(right = 10.px),
            pathData = icon,
            color = iconColor
        )
        SpanText(
            text = title,
            modifier = Modifier
                .id(Res.Id.navigationText)
                .thenIf(selected, Modifier.color(contentColor))
                .fontFamily(Res.Strings.FONT_FAMILY).fontSize(16.px)
        )
    }
}