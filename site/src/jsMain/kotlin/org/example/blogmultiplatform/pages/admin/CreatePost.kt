package org.example.blogmultiplatform.pages.admin

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.ComponentVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.layouts.AdminPageLayout
import org.example.blogmultiplatform.components.widgets.AppButton
import org.example.blogmultiplatform.components.widgets.AppDropDown
import org.example.blogmultiplatform.components.widgets.CustomInputField
import org.example.blogmultiplatform.components.widgets.CustomTextArea
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun CreatePostPage() {
    val breakpoint = rememberBreakpoint()
    var popular by remember { mutableStateOf(false) }
    var main by remember { mutableStateOf(false) }
    var sponsored by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var pastImageUrl by remember { mutableStateOf(false) }
    var postContent by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    AdminPageLayout {
        Column(
            modifier = Modifier.fillMaxSize()
                .maxWidth(700.px)
                .margin(leftRight = 16.px)
                .align(Alignment.Center)
                .padding(topBottom = 50.px),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleGrid(numColumns = numColumns(base = 2, sm = 3), variant = ComponentVariant.Empty) {
                SwitchTile(text = "Popular", checked = popular) {
                    popular = it
                }
                SwitchTile(text = "Main", checked = main) {
                    main = it
                }
                SwitchTile(text = "Sponsored", checked = sponsored) {
                    sponsored = it
                }
            }
            CustomInputField(
                modifier = Modifier.margin(top = 20.px).fillMaxWidth()
                    .background(AppColors.LightGrey.rgb),
                inputType = InputType.Text, value = title,
                onTextChanged = {
                    title = it
                },
                placeholder = "Title",
            )
            CustomInputField(
                modifier = Modifier.margin(top = 14.px).fillMaxWidth()
                    .background(AppColors.LightGrey.rgb),
                inputType = InputType.Text, value = subtitle,
                onTextChanged = {
                    subtitle = it
                },
                placeholder = "Subtitle",
            )
            AppDropDown(
                modifier = Modifier.margin(top = 14.px).fillMaxWidth(),
                selectedItem = selectedCategory,
                options = Category.entries
            ) {
                selectedCategory = it
            }
            SwitchTile(
                modifier = Modifier.margin(top = 14.px).alignSelf(AlignSelf.SelfStart),
                checked = pastImageUrl,
                text = "Paste an Image URL instead",
                onCheckChanged = {
                    pastImageUrl = it
                })
            Row(modifier = Modifier.fillMaxWidth().margin(top = 14.px)) {
                CustomInputField(
                    modifier = Modifier.margin(right = 14.px).fillMaxWidth()
                        .background(AppColors.LightGrey.rgb).weight(1),
                    inputType = InputType.Url, value = subtitle,
                    onTextChanged = {
                        subtitle = it
                    },
                    readOnly = !pastImageUrl,
                    placeholder = "Image Url",
                )
                AppButton(modifier = Modifier.width(92.px), text = "Upload") {

                }
            }
            Row(
                modifier = Modifier.margin(top = 14.px).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.margin(right = 14.px)) {

                }
                Spacer()
                AppButton(
                    modifier = Modifier
                        .width(97.px)
                        .background(AppColors.LightGrey.rgb)
                        .color(AppColors.HalfBlack.rgb), text = "Preview"
                ) {

                }
            }

            CustomTextArea(
                modifier = Modifier
                    .height(700.px)
                    .margin(top = 14.px)
                    .fillMaxWidth()
                    .background(AppColors.LightGrey.rgb),
                value = "", placeholder = "Type here..."
            ) {
                postContent = it
            }
            AppButton(modifier = Modifier.margin(top = 14.px).fillMaxWidth(), text = "Create") {

            }
        }
    }
}

@Composable
private fun SwitchTile(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
    Row(modifier = Modifier.margin(right = 24.px).then(modifier), verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckChanged,
            size = SwitchSize.LG,
            modifier = Modifier.margin(right = 10.px)
        )
        SpanText(
            text = text,
            modifier = Modifier.fontSize(14.px).color(AppColors.HalfBlack.rgb)
                .fontFamily(Res.Strings.FONT_FAMILY)
        )
    }
}