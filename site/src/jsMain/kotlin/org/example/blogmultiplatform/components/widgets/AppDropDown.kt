package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.core.AppColors
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Ul

@Composable
fun <T> AppDropDown(
    modifier: Modifier = Modifier,
    selectedItem: T? = null,
    options: List<T> = emptyList(),
    optionName: ((T) -> String)? = null,
    onSelect: (T) -> Unit = {}
) {
    val buildOptionName: (T) -> String = { option: T ->
        optionName?.invoke(option) ?: option.toString()
    }
    Box(
        modifier = modifier.height(53.px).background(AppColors.LightGrey.rgb)
            .cursor(Cursor.Pointer)
            .attrsModifier {
                attr("data-bs-toggle", "dropdown")
                attr("aria-expanded", "false")
            }
            .padding(topBottom = 16.px, leftRight = 20.px)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .attrsModifier { attr("role", "button") },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpanText(selectedItem?.let { buildOptionName(it) } ?: "Select Item",
                modifier = Modifier.margin(right = 14.px).fontSize(14.px))
            Box(modifier = Modifier.classNames("dropdown-toggle"))
        }
        Div(
            attrs = Modifier.classNames("dropdown")
                .toAttrs()
        ) {
            Ul(attrs = Modifier.classNames("dropdown-menu").toAttrs()) {
                for (option in options) {
                    Li(attrs = Modifier
                        .onClick {
                            onSelect(option)
                        }
                        .toAttrs {
                            classes("dropdown-item")
                            if (selectedItem == option) classes("active")
                        }) {
                        SpanText(buildOptionName(option))
                    }
                }
            }
        }
    }
}