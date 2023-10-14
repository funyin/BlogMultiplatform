package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.forms.Checkbox
import com.varabyte.kobweb.silk.components.forms.CheckboxSize
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.ComponentVariant
import com.varabyte.kobweb.silk.components.style.addVariant
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.datetime.Instant
import kotlinx.datetime.toJSDate
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.res.Res
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

val PostPreviewStyle by ComponentStyle {
    cssRule(" #${Res.Id.thumbnailVert}") {
        Modifier
            .background(AppColors.LightGrey.rgb)
            .fillMaxWidth()
            .height(320.px)
            .margin(bottom = 16.px)
    }
    cssRule(" #${Res.Id.thumbnailHor}") {
        Modifier
            .background(AppColors.LightGrey.rgb)
            .height(178.px)
            .width(316.px)
            .margin(right = 20.px)
            .display(DisplayStyle.None)
    }
    cssRule(" #${Res.Id.postDate}") {
        Modifier
            .fontSize(10.px).color(color = Color.rgb(0x7a7a7a))
    }
    cssRule(" #${Res.Id.postTitle}") {
        Modifier
            .maxLines(3)
            .color(Colors.Black)
            .fontSize(20.px)
            .fontWeight(FontWeight.Bold)
    }
    cssRule(" #${Res.Id.postSubtitle}") {
        Modifier.maxLines(2)
    }
}
val PostPreviewDarkVariant by PostPreviewStyle.addVariant {
    cssRule(" #${Res.Id.postDate}") {
        Modifier
            .color(AppColors.HalfWhite.rgb)
    }
    cssRule(" #${Res.Id.postTitle}") {
        Modifier
            .color(Colors.White)
    }
    cssRule(" #${Res.Id.postSubtitle}") {
        Modifier
            .color(Colors.White)
    }
}

/**
 * The secondary main posts on the home screen header
 */
val PostPreviewHeavyVariant by PostPreviewStyle.addVariant {
    cssRule(" #${Res.Id.postDate}") {
        Modifier
            .fontSize(14.px)
    }
    cssRule(" #${Res.Id.postTitle}") {
        Modifier
            .maxLines(1)
            .fontWeight(FontWeight.Black)
    }
    cssRule(" #${Res.Id.postSubtitle}") {
        Modifier.maxLines(3)
    }
}

val PostPreviewHorizontalVariant by PostPreviewStyle.addVariant {
    cssRule(" #${Res.Id.thumbnailVert}") {
        Modifier
            .display(DisplayStyle.None)
    }
    cssRule(" #${Res.Id.thumbnailHor}") {
        Modifier
            .display(DisplayStyle.Initial)
    }
}

val PostPreviewLargeVariant by PostPreviewStyle.addVariant {
    cssRule(" #${Res.Id.thumbnailVert}") {
        Modifier
            .height(412.px)
    }
    cssRule(" #${Res.Id.thumbnailHor}") {
        Modifier
            .height(412.px)
    }
    cssRule(" #${Res.Id.postTitle}") {
        Modifier
            .fontSize(36.px)

    }
}
val PostPreviewBlueTitleVariant by PostPreviewStyle.addVariant {
    cssRule(" #${Res.Id.postTitle}") {
        Modifier
            .color(AppColors.Blue.rgb)
    }
}

private val Res.Id.Companion.postDate: String
    get() = "postDate"
private val Res.Id.Companion.postTitle: String
    get() = "postTitle"
private val Res.Id.Companion.postSubtitle: String
    get() = "postSubtitle"

private val Res.Id.Companion.thumbnailVert: String
    get() = "thumbnailVert"
private val Res.Id.Companion.thumbnailHor: String
    get() = "thumbnailHor"

@Composable
fun PostPreview(
    modifier: Modifier = Modifier,
    postLight: PostLight,
    selectable: Boolean = false,
    checked: Boolean = false,
    variant: ComponentVariant = ComponentVariant.Empty,
    onCheckChanged: (Boolean) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Row(modifier = Modifier.fillMaxWidth()
        .thenIf(
            selectable, Modifier.padding(20.px)
                .border(
                    width = 2.px,
                    color = if (checked) AppColors.Primary.rgb else Color.rgb(0xEDEDED),
                    style = LineStyle.Solid
                )
                .borderRadius(4.px)
        )
        .cursor(Cursor.Pointer)
        .transition(
            CSSTransition(TransitionProperty.of("border"), duration = 300.ms),
            CSSTransition(TransitionProperty.of("padding"), duration = 500.ms)
        )
        .onClick {
            if (selectable)
                onCheckChanged(!checked)
            else
                onClick()
        }
        .then(PostPreviewStyle.toModifier(variant))
        .then(modifier)) {
        PostImage(
            modifier = Modifier.id(Res.Id.thumbnailHor),
            thumbnail = postLight.thumbnail
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            PostImage(modifier = Modifier.id(Res.Id.thumbnailVert), thumbnail = postLight.thumbnail)
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                SpanText(
                    text = Instant.fromEpochMilliseconds(postLight.date).toJSDate()
                        .toLocaleDateString(),
                    modifier = Modifier
                        .id(Res.Id.postDate)
                )
                SpanText(
                    text = postLight.title,
                    modifier = Modifier
                        .id(Res.Id.postTitle)
                )
                SpanText(
                    text = postLight.subtitle,
                    modifier = Modifier
                        .id(Res.Id.postSubtitle)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().margin(top = 8.px),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SpanText(
                        text = postLight.category,
                        modifier = Modifier
                            .padding(topBottom = 9.px, leftRight = 14.px)
                            .border(
                                width = 0.86.px,
                                style = LineStyle.Solid,
                                color = Color.rgb(0x7A7A7A)
                            )
                            .borderRadius(55.px)
                            .color(Color.rgb(0x7A7A7A))
                            .fontSize(12.px)
                    )
                    if (selectable)
                        Checkbox(
                            checked = checked,
                            size = CheckboxSize.LG, onCheckedChange = onCheckChanged
                        )
                }
            }
        }
    }
}

@Composable
private fun PostImage(modifier: Modifier, thumbnail: String) {
    Box(
        modifier = modifier
    ) {
        Image(
            src = thumbnail,
            desc = "thumbnail",
            modifier = Modifier.fillMaxSize()
                .objectFit(ObjectFit.Cover),
        )
    }
}