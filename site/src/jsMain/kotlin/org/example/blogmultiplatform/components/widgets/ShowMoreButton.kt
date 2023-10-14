package org.example.blogmultiplatform.pages.admin.posts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.models.UiState
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Progress

@Composable
fun ShowMoreButton(uiState: UiState<Any>, onClick: () -> Unit = {}) {
    val modifier = Modifier.margin(topBottom = 50.px)
    if (uiState.isLoading)
        Progress(
            attrs = Modifier.width(200.px)
                .then(modifier)
                .toAttrs()
        )
    else
        SpanText("Show More", modifier = Modifier.fontSize(16.px).then(modifier)
            .cursor(Cursor.Pointer)
            .fontWeight(FontWeight.Bold)
            .onClick {
                onClick()
            }
            .thenIf(uiState.isInitial, Modifier.display(DisplayStyle.None))
        )
}