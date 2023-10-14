package org.example.blogmultiplatform.components.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaMagnifyingGlass
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import org.example.blogmultiplatform.components.widgets.CategoryMenuItems
import org.example.blogmultiplatform.components.widgets.SearchInput
import org.example.blogmultiplatform.components.widgets.SearchInputDarkVariant
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.res.PAGE_WIDTH
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.TOP_PANEL_HEIGHT
import org.example.blogmultiplatform.res.logo
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun HeaderSection(
    breakpoint: Breakpoint, state: HeaderState,
    onStateChanged: (HeaderState) -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth().background(AppColors.Secondary.rgb), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .maxWidth(Res.Dimens.PAGE_WIDTH.px)
                .background(AppColors.Secondary.rgb),
            contentAlignment = Alignment.Center
        ) {
            Header(state, onStateChanged, breakpoint)
        }
    }
}

data class HeaderState(val search: String = "", val onMenuClick: () -> Unit)

@Composable
fun Header(
    state: HeaderState,
    onStateChanged: (HeaderState) -> Unit,
    breakpoint: Breakpoint,
) {
    var showSearchOverlay by remember { mutableStateOf(false) }
    val showFullSearch = breakpoint > Breakpoint.SM
    LaunchedEffect(breakpoint) {
        if (showFullSearch) {
            showSearchOverlay = false
        }
    }
    val searchInput: @Composable () -> Unit = {
        SearchInput(value = state.search, variant = SearchInputDarkVariant) {
            onStateChanged(state.copy(search = it))
        }
    }
    val pageContext = rememberPageContext()
    Row(
        modifier = Modifier.fillMaxWidth()
            .fillMaxWidth(if (breakpoint > Breakpoint.MD) 80.percent else 90.percent)
            .height(Res.Dimens.TOP_PANEL_HEIGHT.px),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (!showSearchOverlay) {
            if (breakpoint <= Breakpoint.MD)
                FaBars(size = IconSize.XL, modifier = Modifier.margin(right = 24.px).color(Colors.White)
                    .cursor(Cursor.Pointer)
                    .onClick {
                        state.onMenuClick()
                    }
                )
            Image(
                src = Res.Images.logo, desc = "logo", modifier = Modifier.margin(right = 50.px)
                    .width(if (breakpoint >= Breakpoint.SM) 100.px else 70.px)
                    .cursor(Cursor.Pointer)
                    .onClick {
                        pageContext.router.navigateTo("/")
                    }
            )
            if (breakpoint >= Breakpoint.LG) {
                CategoryMenuItems(horizontal = true)
            }
            Spacer()
            if (showFullSearch)
                searchInput()
            else
                FaMagnifyingGlass(size = IconSize.XL, modifier = Modifier.color(AppColors.Primary.rgb)
                    .cursor(Cursor.Pointer)
                    .onClick {
                        showSearchOverlay = true
                    })
        } else {
            FaXmark(size = IconSize.XL, modifier = Modifier.margin(right = 24.px).color(Colors.White)
                .cursor(Cursor.Pointer)
                .onClick {
                    showSearchOverlay = false
                }
            )
            searchInput()
        }
    }
}