package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw

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
        Box(modifier = Modifier.padding(24.px).background(Colors.White), contentAlignment = Alignment.Center) {
            SpanText(
                text = message,
                modifier = Modifier.fillMaxWidth().textAlign(TextAlign.Center).fontFamily(Res.Strings.FONT_FAMILY)
                    .fontSize(16.px)
            )
        }
    }
}