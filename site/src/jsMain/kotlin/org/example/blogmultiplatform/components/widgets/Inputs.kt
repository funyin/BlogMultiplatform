package org.example.blogmultiplatform.components.widgets

//import org.jetbrains.compose.web.dom.Input
import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.forms.UnstyledInputVariant
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.*
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.searchIcon
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.readOnly
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.TextArea

val CustomInputStyle by ComponentStyle {
    base {
        Modifier.border(
            width = 2.px,
            style = LineStyle.Solid,
            color = Colors.Transparent
        )
            .width(350.px).height(54.px)
            .padding(leftRight = 20.px, topBottom = 16.px)
            .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
            .background(Colors.White)
            .borderRadius(0.375.cssRem)
            .border(style = LineStyle.None, width = 0.px)
            .outline(style = LineStyle.None, width = 0.px)
            .fontSize(14.px)
            .transition(CSSTransition(property = "border", duration = 300.ms))
    }
    focus {
        Modifier.border(
            width = 2.px,
            style = LineStyle.Solid,
            color = AppColors.Primary.rgb
        )
    }
}

val CustomInputGreyVariant by CustomInputStyle.addVariant {
    base {
        Modifier
            .background(Color.rgb(0xE9E9E9))
            .borderRadius(35.px)
    }
}

val CustomInputRoundedVariant by CustomInputStyle.addVariant {
    base {
        Modifier.borderRadius(35.px)
    }
}

@Composable
fun <T : Any> CustomInputField(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    inputType: InputType<T>,
    value: T,
    readOnly: Boolean = false,
    name: String? = null,
    variant: ComponentVariant = ComponentVariant.Empty,
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
        variant = UnstyledInputVariant,
        modifier = CustomInputStyle.toModifier(variant)
            .then(modifier).attrsModifier {
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
        attrs = CustomInputStyle.toModifier().minHeight(54.px).padding(leftRight = 20.px, topBottom = 16.px).outline(
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

    cssRule(":not(:hover) input") {
        Modifier
            .color(AppColors.HalfBlack.rgb)
    }
}

val SearchInputDarkVariant by SearchInputStyle.addVariant {
    base {
        Modifier.background(AppColors.Tertiary.rgb)
            .border(width = 1.px, color = AppColors.Secondary.rgb, style = LineStyle.Solid)
            .transition(CSSTransition(TransitionProperty.of("border"), duration = 200.ms))
    }
    cssRule(" input") {
        Modifier
            .color(Colors.White)
    }
}

@Composable
fun SearchInput(
    modifier: Modifier = Modifier,
    value: String,
    variant: ComponentVariant = ComponentVariant.Empty,
    onChange: (String) -> Unit
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
            onValueChanged = onChange,
            modifier = Modifier.weight(1)
                .fillMaxHeight()
                .border(style = LineStyle.None, width = 0.px)
                .padding(top = 17.px, bottom = 17.px, left = 14.px, right = 17.px)
                .onFocusIn { hasFocus = true }.onFocusOut { hasFocus = false },
            focusBorderColor = Colors.Transparent,
            placeholder = "Search..."
        )
    }
}