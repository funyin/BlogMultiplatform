package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*

@Composable
fun MessagePopup(
    message: String,
    onDialogDismissed: () -> Unit
) {
    Box(
        modifier = Modifier.width(100.vw).height(100.vh)
            .position(Position.Fixed)
            .zIndex(19),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize().background(AppColors.HalfBlack.rgb).onClick {
            onDialogDismissed()
        })
        Box(
            modifier = Modifier.padding(24.px).background(Colors.White).margin(10.percent),
            contentAlignment = Alignment.Center
        ) {
            SpanText(
                text = message,
                modifier = Modifier.fillMaxWidth().textAlign(TextAlign.Center).fontFamily(Res.Strings.FONT_FAMILY)
                    .fontSize(16.px)
            )
        }
    }
}

@Composable
fun LinkPopup(
    message: String = "",
    onDialogDismissed: () -> Unit,
    onLinkAdded: (text: String, link: String) -> Unit
) {
    var text by remember { mutableStateOf(message) }
    var link by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.width(100.vw).height(100.vh)
            .position(Position.Fixed)
            .zIndex(19),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize().background(AppColors.HalfBlack.rgb).onClick {
            onDialogDismissed()
        })
        Column(
            modifier = Modifier.padding(24.px).background(Colors.White).width(500.px).borderRadius(6.px),
        ) {
            CustomInputField(
                modifier = Modifier.fillMaxWidth(),
                inputType = InputType.Text,
                value = text,
                placeholder = "Text"
            ) {
                text = it
            }
            CustomInputField(
                modifier = Modifier.fillMaxWidth().margin(top = 16.px),
                inputType = InputType.Url,
                value = link,
                placeholder = "Link"
            ) {
                link = it
            }
            AppButton(modifier = Modifier.fillMaxWidth().margin(top = 20.px), text = "Done") {
                onLinkAdded(text, link)
            }
        }
    }
}