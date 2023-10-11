package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.breakpoint.ResponsiveValues
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.CSSpxValue
import org.jetbrains.compose.web.css.px

@Composable
fun ResponsiveValues<Int>.itemSpace(
    index: Int,
    itemCount: Int,
    horizontalGap: CSSpxValue = 0.px,
    verticalGap: CSSpxValue = 0.px
): Modifier {
    val breakPoint = rememberBreakpoint()
    val columnCount: Int = when (breakPoint) {
        Breakpoint.ZERO -> this.base
        Breakpoint.SM -> this.sm
        Breakpoint.MD -> md
        Breakpoint.LG -> this.lg
        Breakpoint.XL -> this.xl
    }
    val rowCount = itemCount / columnCount
    val rowIndex = index / columnCount

//    val gapValue = horizontalGap.value
//    val lastInRow = (index + 1) % columnCount == 0
//    val firstInRow = index % columnCount == 0
    return Modifier
        .margin(
            right = if ((index + 1) % columnCount != 0) horizontalGap else 0.px,
            bottom = if (rowIndex != rowCount + 1) verticalGap else 0.px
        )
}

fun Modifier.maxLines(count: Int): Modifier {
    return styleModifier {
        property("display", "-webkit-box")
        property("-webkit-line-clamp", count)
        property("line-clamp", count)
        property("-webkit-box-orient", "vertical")
    }.overflow(Overflow.Hidden)
}