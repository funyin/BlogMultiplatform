package org.example.blogmultiplatform

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.init.InitKobweb
import com.varabyte.kobweb.core.init.InitKobwebContext
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.css.vh

@InitSilk
fun initSilk(ctx: InitSilkContext) {
    // Configure silk here
}

@InitKobweb
fun initKobweb(ctx: InitKobwebContext) {
    ctx.router.addRouteInterceptor {
//        if (fragment?.contains(".") == false)
//            path = path.lowercase()
    }
}

val baseStyle by ComponentStyle {
    cssRule(" .silk-span-text, p") {
        Modifier.fontFamily(Res.Strings.FONT_FAMILY)
    }
}

@App
@Composable
fun MyApp(content: @Composable () -> Unit) {
    SilkApp {
        Surface(
            SmoothColorStyle.toModifier()
                .minHeight(100.vh)
                .then(baseStyle.toModifier())
        ) {
            content()
        }
    }
}
