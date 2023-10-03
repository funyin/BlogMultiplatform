package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.dom.Path
import com.varabyte.kobweb.compose.dom.Svg
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.svgParent
import org.example.blogmultiplatform.res.vectorIcon
import org.jetbrains.compose.web.css.px

@Composable
fun VectorIcon(
    modifier: Modifier = Modifier,
    pathData: String, color: String
) {
    Svg(attrs = modifier
        .id(Res.Id.svgParent)
        .width(24.px)
        .height(24.px)
        .toAttrs {
            attr(attr = "viewBox", "0 0 24 24")
            attr(attr = "fill", "none")
        }) {
        Path(attrs = Modifier
            .id(Res.Id.vectorIcon)
            .toAttrs {
                attr("d", pathData)
                attr("stroke", color)
                attr("stroke-width", "2")
                attr("stroke-linecap", "round")
                attr("stroke-linejoin", "round")
            })
    }
}