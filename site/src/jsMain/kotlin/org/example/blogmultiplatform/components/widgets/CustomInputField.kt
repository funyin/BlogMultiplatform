package org.example.blogmultiplatform.components.widgets

//import org.jetbrains.compose.web.dom.Input
import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.style.toModifier
import org.example.blogmultiplatform.styles.LoginInputStyle
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.readOnly
import org.jetbrains.compose.web.css.LineStyle
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
    Input(
        type = inputType,
        value = value,
        onValueChanged = {
            onTextChanged(it)
        },
        readOnly = readOnly,
        placeholder = placeholder,
        modifier = LoginInputStyle.toModifier()
            .width(350.px)
            .height(54.px)
            .padding(leftRight = 20.px, topBottom = 16.px)
            .outline(
                width = 0.px,
                style = LineStyle.None,
                color = Colors.Transparent
            )
            .background(Colors.White)
            .fontSize(14.px)
            .then(modifier)
            .attrsModifier {
                name?.let {
                    attr("name", it)
                }
            }
    )
}

@Composable
fun CustomTextArea(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String = "",
    readOnly: Boolean = false,
    onTextChanged: (String) -> Unit
) {
    TextArea(
        value = value,
        attrs = LoginInputStyle.toModifier()
            .minHeight(54.px)
            .padding(leftRight = 20.px, topBottom = 16.px)
            .outline(
                width = 0.px,
                style = LineStyle.None,
                color = Colors.Transparent
            )
            .background(Colors.White)
            .fontSize(14.px)
            .then(modifier)
            .toAttrs {
                onInput {
                    onTextChanged(it.value)
                }
                placeholder(placeholder)
                if (readOnly)
                    readOnly()
            }
    )
}