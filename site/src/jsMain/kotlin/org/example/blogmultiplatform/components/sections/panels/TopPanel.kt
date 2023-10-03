package org.example.blogmultiplatform.components.sections.panels

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.TOP_PANEL_HEIGHT
import org.example.blogmultiplatform.res.logo
import org.jetbrains.compose.web.css.px

@Composable
fun TopPanel(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth().height(Res.Dimens.TOP_PANEL_HEIGHT.px)
            .padding(leftRight = 24.px)
            .background(AppColors.Secondary.rgb),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FaBars(
            modifier = Modifier.margin(right = 24.px)
                .color(Colors.White)
                .cursor(Cursor.Pointer)
                .onClick {
                    onClick()
                },
            size = IconSize.XL
        )

        Image(modifier = Modifier.width(80.px), src = Res.Images.logo, desc = "Logo")
    }
}