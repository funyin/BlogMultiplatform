package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.css.px

@Composable
fun SwitchTile(
    modifier: Modifier = Modifier,
    switchSize: SwitchSize = SwitchSize.MD,
    text: String,
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
    Row(modifier = Modifier.margin(right = 24.px).then(modifier), verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckChanged,
            size = switchSize,
            modifier = Modifier.margin(right = 10.px)
        )
        SpanText(
            text = text,
            modifier = Modifier.fontSize(14.px).color(AppColors.HalfBlack.rgb).fontFamily(Res.Strings.FONT_FAMILY)
        )
    }
}