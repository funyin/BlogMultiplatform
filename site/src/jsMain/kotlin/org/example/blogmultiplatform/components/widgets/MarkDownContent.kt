package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.blogmultiplatform.res.Res
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

private val Res.Id.Companion.markdownContent: String
    get() = "markdownContent"

val MarkdownContentStyle by ComponentStyle {
    cssRule(" pre") {
        Modifier.background(Color.rgb(0x0d1117))
            .padding(8.px)
            .borderRadius(4.px)
    }
    cssRule(" ") {
        Modifier.maxWidth(100.percent)
    }
}

@Composable
fun MarkDownContent(modifier: Modifier = Modifier, markDown: String) {
    LaunchedEffect(markDown) {
        injectMarkDown(markDown, containerId = Res.Id.markdownContent)
    }
    Div(attrs = modifier.id(Res.Id.markdownContent).then(MarkdownContentStyle.toModifier()).toAttrs())
}

fun CoroutineScope.injectMarkDown(markDown: String, containerId: String) {
    val flavour = CommonMarkFlavourDescriptor()
    val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(markDown)
    val html = HtmlGenerator(markDown, parsedTree, flavour).generateHtml()
    document.getElementById(containerId)?.innerHTML = html
    launch {
        delay(100)
        js("hljs.highlightAll()") as? Unit
    }
}