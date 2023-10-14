package org.example.blogmultiplatform.res

import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint

val Res.Dimens.Companion.PAGE_WIDTH: Int
    get() = 1920
val Res.Dimens.Companion.SIDE_PANEL_WIDTH: Int
    get() = 250

val Res.Dimens.Companion.TOP_PANEL_HEIGHT: Int
    get() = 100
fun Res.Dimens.Companion.MAX_WIDTH(breakpoint: Breakpoint): Int {
    return if (breakpoint > Breakpoint.MD) 80 else 90
}