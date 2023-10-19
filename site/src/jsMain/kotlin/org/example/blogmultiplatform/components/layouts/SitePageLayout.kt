package org.example.blogmultiplatform.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.sections.FooterSection
import org.example.blogmultiplatform.components.sections.HeaderSection
import org.example.blogmultiplatform.components.sections.HeaderState
import org.example.blogmultiplatform.components.sections.panels.OverflowSidePanel
import org.example.blogmultiplatform.components.widgets.CategoryMenuItems

@Composable
fun SitePageLayout(content: @Composable ColumnScope.(String) -> Unit) {
    val breakpoint = rememberBreakpoint()
    val pageContext = rememberPageContext()
    val search = pageContext.route.params["search"] ?: ""
    val hideSection = pageContext.route.params["hideSections"].toBoolean()
    var showSidePanel by remember { mutableStateOf(false) }
    var headerState by remember(search) {
        mutableStateOf(HeaderState(search = search) {
            showSidePanel = false
        })
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!hideSection)
                HeaderSection(breakpoint, state = headerState) {
                    headerState = it
                }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                content(headerState.search)
            }
            if (!hideSection)
                FooterSection()
        }
        if (!showSidePanel && !hideSection)
            OverflowSidePanel(
                modifier = Modifier.pointerEvents(PointerEvents.Auto),
                onMenuClose = {
                    showSidePanel = false
                }) {
                CategoryMenuItems(horizontal = false)
            }
    }
}