package org.example.blogmultiplatform.pages.admin.posts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.coroutines.delay
import org.example.blogmultiplatform.components.widgets.AuthGuard
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.checkMark
import org.example.blogmultiplatform.res.posts
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw

@Page
@Composable
fun SuccessPage() {
    val context = rememberPageContext()
    LaunchedEffect(Unit){
        delay(3000)
        context.router.navigateTo(Res.Routes.posts)
    }
    AuthGuard {
        Box(
            modifier = Modifier
                .height(100.vh).width(100.vw).position(Position.Fixed),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(src = Res.Images.checkMark, modifier = Modifier.size(156.px))
                SpanText(
                    text = "Post Successfully Created!", modifier = Modifier.margin(top = 24.px).fontSize(24.px).color(
                        Colors.Black
                    )
                )
                SpanText(
                    text = "Redirecting you back...",
                    modifier = Modifier.margin(top = 6.px).fontSize(18.px).color(Colors.Black.copy(alpha = 125))
                )
            }
        }
    }
}