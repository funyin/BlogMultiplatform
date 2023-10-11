package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div

@Composable
fun Spinner(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize().maxHeight(100.vh).then(modifier)
            .padding(topBottom = 50.px), contentAlignment = Alignment.Center
    ) {
        Div(
            attrs = Modifier
                .classNames("spinner-border")
                .toAttrs()
        ) {
            SpanText(text = "Loading..", modifier = Modifier.classNames("visually-hidden"))
        }
    }
}