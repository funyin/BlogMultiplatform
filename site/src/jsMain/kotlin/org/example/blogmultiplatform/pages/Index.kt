package org.example.blogmultiplatform.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.sections.HeaderSection
import org.example.blogmultiplatform.components.sections.panels.OverflowSidePanel
import org.example.blogmultiplatform.components.widgets.CategoryMenuItems

@Page
@Composable
fun HomePage() {
    val breakpoint = rememberBreakpoint()
    var showSidePanel by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSection(breakpoint) {
                showSidePanel = true
            }
        }
        if (showSidePanel)
            OverflowSidePanel(
                modifier = Modifier.pointerEvents(PointerEvents.Auto),
                onMenuClose = {
                    showSidePanel = false
                }) {
                CategoryMenuItems(horizontal = false)
            }
    }
}
