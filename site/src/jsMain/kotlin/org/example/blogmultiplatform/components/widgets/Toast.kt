package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.coroutines.delay
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.css.*

@Composable
fun Toast(message: String, show: Boolean, close: () -> Unit) {
    var translateX by remember { mutableStateOf((100).percent) }
    var opacity by remember { mutableStateOf(0.percent) }
    LaunchedEffect(show) {
        if (show) {
            translateX = 0.percent
            opacity = 100.percent
            delay(2000)
            close()
        } else {
            translateX = (100).percent
            opacity = 0.percent
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .maxWidth(100.vw)
            .maxHeight(100.vh)
            .position(Position.Fixed)
            .pointerEvents(PointerEvents.None)
            .thenIf(show, Modifier.pointerEvents(PointerEvents.Initial).onClick {
                close()
            }),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier.background(AppColors.Tertiary.rgb)
                .borderRadius(4.px)
                .padding(16.px)
                .margin(bottom = 50.px)
                .pointerEvents(PointerEvents.Auto)
                .translateY(translateX)
                .opacity(opacity)
                .transition(
                    CSSTransition(TransitionProperty.of("translate"), duration = 300.ms),
                    CSSTransition(TransitionProperty.of("opacity"), duration = 400.ms),
                )
        ) {
            SpanText(message, modifier = Modifier.fontFamily(Res.Strings.FONT_FAMILY).color(Colors.White))
        }
    }
}