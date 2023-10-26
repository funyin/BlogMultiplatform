package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignSelf

@Composable
fun Dialog(show: Boolean, message: String, onClose: () -> Unit, onPositive: () -> Unit) {
    var translateY by remember { mutableStateOf((-100).px) }
    var opacity by remember { mutableStateOf((0).percent) }
    var showInternal by remember { mutableStateOf(false) }
    LaunchedEffect(show) {
        if (show) {
            showInternal = true
            translateY = 0.px
            opacity = 100.percent
        } else {
            translateY = (-100).px
            opacity = 0.percent
        }
    }
    if (showInternal)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .maxHeight(100.vh)
                .position(Position.Fixed)
                .pointerEvents(PointerEvents.None)
                .top(0.px),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(modifier = Modifier.fillMaxSize()
                .opacity(opacity)
                .transition(
                    CSSTransition(TransitionProperty.of("translate"), duration = 300.ms),
                    CSSTransition(TransitionProperty.of("opacity"), duration = 400.ms),
                )
                .onTransitionEnd {
                    showInternal = show
                }
                .background(rgba(0, 0, 0, 0.4))
                .pointerEvents(PointerEvents.None)
                .thenIf(show, Modifier.pointerEvents(PointerEvents.Initial).onClick {
                    onClose()
                })
            )
            Column(
                modifier = Modifier.margin(top = 30.px)
                    .width(500.px)
                    .background(Color.white).borderRadius(8.px)
                    .padding(12.px)
                    .pointerEvents(PointerEvents.Auto)
                    .translateY(translateY)
                    .opacity(opacity)
                    .transition(
                        CSSTransition(TransitionProperty.of("translate"), duration = 400.ms),
                        CSSTransition(TransitionProperty.of("opacity"), duration = 400.ms),
                    )
            ) {
                FaXmark(size = IconSize.LG,
                    modifier = Modifier
                        .alignSelf(AlignSelf.SelfEnd)
                        .lineHeight(LineHeight.Initial)
                        .cursor(Cursor.Pointer)
                        .onClick {
                            onClose()
                        })
                SpanText(
                    text = message,
                    modifier = Modifier.fontSize(18.px).fontFamily(Res.Strings.FONT_FAMILY)
                        .margin(top = 20.px, bottom = 30.px)
                )
                Row(modifier = Modifier.alignSelf(AlignSelf.SelfEnd).gap(16.px)) {
                    AppButton(
                        text = "No",
                        modifier = Modifier.width(100.px).height(40.px),
                        variant = AppButtonTextVariant
                    ) {
                        onClose()
                    }
                    AppButton(
                        text = "Yes",
                        modifier = Modifier.width(100.px).height(40.px),
                        variant = AppButtonTextVariant
                    ) {
                        onPositive()
                    }
                }
            }
        }
}