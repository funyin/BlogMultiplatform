package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh

@Composable
fun AppEmptyView(modifier: Modifier = Modifier, text: String) {
    Column(
        modifier = Modifier.fillMaxSize().maxHeight(100.vh).then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpanText(
            text,
            modifier = Modifier.fontSize(18.px).fontWeight(FontWeight.SemiBold)
        )
    }
}

@Composable
fun AppErrorView(modifier: Modifier = Modifier, text: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().maxHeight(100.vh).then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpanText(
            text,
            modifier = Modifier.fontSize(18.px).fontWeight(FontWeight.SemiBold).textAlign(TextAlign.Center)
        )
        AppButton(text = "Retry", modifier = Modifier.margin(top = 24.px)) {
            onRetry()
        }
    }
}

@Composable
fun AppLoadingView(modifier: Modifier = Modifier) {
    Spinner(modifier = modifier)
}