package org.example.blogmultiplatform.components.sections.panels

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.TOP_PANEL_HEIGHT
import org.example.blogmultiplatform.res.logo
import org.jetbrains.compose.web.css.*

@Composable
fun OverflowSidePanel(
    modifier: Modifier = Modifier,
    onMenuClose: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val breakPoint = rememberBreakpoint()
    var translateX by remember { mutableStateOf((-100).percent) }
    var opactiy by remember { mutableStateOf(0.percent) }
    val coroutineScope = rememberCoroutineScope()
    val pageContext = rememberPageContext()
    LaunchedEffect(breakPoint) {
        translateX = 0.percent
        opactiy = 100.percent
        if (breakPoint > Breakpoint.MD)
            onMenuClose()
    }
    val closeMenu = {
        coroutineScope.launch {
            translateX = (-100).percent
            opactiy = 0.percent
            delay(301)
            onMenuClose()
        }
    }
    val route = remember { pageContext.route }

    LaunchedEffect(pageContext.route) {
        if (pageContext.route != route)
            closeMenu()
    }

    Box(
        modifier = Modifier.fillMaxWidth()
            .opacity(opactiy)
            .overflow(Overflow.Auto)
            .height(100.vh)
            .position(Position.Fixed)
            .top(0.px)
            .background(AppColors.HalfBlack.rgb)
            .transition(CSSTransition(property = "opacity", duration = 300.ms))
            .then(modifier)
    ) {
        Box(modifier = Modifier.fillMaxSize().onClick {
            closeMenu()
        })
        Column(
            modifier = Modifier.fillMaxHeight()
                .translateX(translateX)
                .transition(CSSTransition(property = "translate", duration = 300.ms))
                .background(AppColors.Secondary.rgb)
                .scrollBehavior(ScrollBehavior.Smooth)
                .width(if (breakPoint > Breakpoint.MD) 50.percent else 25.percent)
                .minWidth(200.px)
        ) {
            Row(
                modifier = Modifier.height(Res.Dimens.TOP_PANEL_HEIGHT.px).padding(all = 24.px).margin(bottom = 60.px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaXmark(size = IconSize.XL,
                    modifier = Modifier.margin(right = 20.px).cursor(Cursor.Pointer).color(Colors.White)
                        .onClick { closeMenu() })
                Image(Res.Images.logo, desc = "Logo", modifier = Modifier.width(80.px)
                    .onClick {
                        pageContext.router.navigateTo("/")
                    })
            }
            Column(modifier = Modifier.padding(all = 24.px)) {
                content()
            }
        }
    }
}