package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.icons.fa.FaPlus
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.PAGE_WIDTH
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.createPost
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

@Composable
fun AddButton() {
    val breakpoint = rememberBreakpoint()
    val context = rememberPageContext()
    Box(
        modifier = Modifier.height(100.vh).fillMaxWidth()
            .maxWidth(Res.Dimens.PAGE_WIDTH.px)
//            .position(Position.Fixed)
            .styleModifier {
                pointerEvents(PointerEvents.None)
            },
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier
                .size(if (breakpoint > Breakpoint.MD) 80.px else 50.px)
                .borderRadius(14.px)
                .cursor(Cursor.Pointer)
                .transition(CSSTransition(property = TransitionProperty.All, duration = 1000.ms))
                .backgroundColor(AppColors.Primary.rgb)
                .styleModifier {
                    pointerEvents(PointerEvents.Auto)
                }
                .margin(
                    right = if (breakpoint > Breakpoint.MD) 40.px else 20.px,
                    bottom = if (breakpoint > Breakpoint.MD) 40.px else 20.px,
                )
                .onClick {
                    context.router.navigateTo(Res.Routes.createPost)
                },
            contentAlignment = Alignment.Center
        ) {
            FaPlus(size = IconSize.LG, modifier = Modifier.color(Colors.White))
        }
    }
}