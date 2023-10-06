package org.example.blogmultiplatform.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.BoxScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.sections.panels.MenuPanel
import org.example.blogmultiplatform.components.widgets.AuthGuard
import org.example.blogmultiplatform.res.PAGE_WIDTH
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.SIDE_PANEL_WIDTH
import org.example.blogmultiplatform.res.TOP_PANEL_HEIGHT
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px

@Composable
fun AdminPageLayout(content: @Composable BoxScope.() -> Unit) {
    AuthGuard {
        val breakPoint = rememberBreakpoint()
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxSize().maxWidth(Res.Dimens.PAGE_WIDTH.px)
                    .padding(
                        left = if (breakPoint > Breakpoint.MD) Res.Dimens.SIDE_PANEL_WIDTH.px else 0.px,
                        top = if (breakPoint <= Breakpoint.MD) Res.Dimens.TOP_PANEL_HEIGHT.px else 0.px
                    ),
            ) {
                content()
            }
            MenuPanel(
                modifier = Modifier.align(Alignment.CenterStart)
                    .position(Position.Fixed)
                    .top(0.px)
                    .zIndex(9)
            )
        }
    }
}