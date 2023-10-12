package org.example.blogmultiplatform.components.widgets

//import org.jetbrains.compose.web.dom.Input
import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.ComponentVariant
import com.varabyte.kobweb.silk.components.style.addVariant
import com.varabyte.kobweb.silk.components.style.toModifier
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.searchIcon
import org.example.blogmultiplatform.styles.LoginInputStyle
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.readOnly
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.TextArea

@Composable
fun <T : Any> CustomInputField(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    inputType: InputType<T>,
    value: T,
    readOnly: Boolean = false,
    name: String? = null,
    onTextChanged: (T) -> Unit = {}
) {
    Input(type = inputType,
        value = value,
        onValueChanged = {
            onTextChanged(it)
        },
        readOnly = readOnly,
        placeholder = placeholder,
        focusBorderColor = AppColors.Primary.rgb,
        modifier = LoginInputStyle.toModifier().width(350.px).height(54.px)
            .padding(leftRight = 20.px, topBottom = 16.px).outline(
                width = 0.px, style = LineStyle.None, color = Colors.Transparent
            ).border(style = LineStyle.None).background(Colors.White).fontSize(14.px).then(modifier).attrsModifier {
                name?.let {
                    attr("name", it)
                }
            })
}

@Composable
fun CustomTextArea(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String = "",
    readOnly: Boolean = false,
    onTextChanged: (String) -> Unit
) {
    TextArea(value = value,
        attrs = LoginInputStyle.toModifier().minHeight(54.px).padding(leftRight = 20.px, topBottom = 16.px).outline(
            width = 0.px, style = LineStyle.None, color = Colors.Transparent
        ).borderRadius(0.375.cssRem).background(Colors.White).fontSize(14.px).then(modifier).toAttrs {
            onInput {
                onTextChanged(it.value)
            }
            placeholder(placeholder)
            if (readOnly) readOnly()
        })
}

val SearchInputStyle by ComponentStyle {
    base {
        Modifier.background(AppColors.LightGrey.rgb).padding(left = 20.px, top = 2.px, bottom = 2.px)
            .borderRadius(25.px).border(style = LineStyle.None, color = AppColors.Primary.rgb, width = 0.px)
    }
}

val SearchInputDarkVariant by SearchInputStyle.addVariant {
    base {
        Modifier.background(AppColors.Tertiary.rgb)
            .border(width = 1.px, color = AppColors.Secondary.rgb, style = LineStyle.Solid)
            .transition(CSSTransition(TransitionProperty.of("border"), duration = 200.ms))
    }
}

@Composable
fun SearchInput(
    modifier: Modifier = Modifier, value: String, variant: ComponentVariant? = null, onChange: (String) -> Unit
) {
    var hasFocus by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth().maxWidth(350.px).height(54.px)
            .thenIf(hasFocus, Modifier.border(width = 1.px, color = AppColors.Primary.rgb, style = LineStyle.Solid))
            .then(SearchInputStyle.toModifier(variant))
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(src = Res.Images.searchIcon, modifier = Modifier.size(20.px).color(Colors.White))
        Input(type = InputType.Search,
            value = value,
            modifier = Modifier.fillMaxHeight().weight(1).color(AppColors.HalfBlack.rgb)
                .border(style = LineStyle.None, width = 0.px)
                .padding(top = 17.px, bottom = 17.px, left = 14.px, right = 17.px).onFocusIn {
                    hasFocus = true
                }.onFocusOut {
                    hasFocus = false
                },
            onValueChanged = onChange,
            focusBorderColor = Colors.Transparent,
            placeholder = "Search..."
        )
    }
}