package org.example.blogmultiplatform.components.sections.panels

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.PointerEvents
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.pointerEvents
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint

@Composable
fun MenuPanel(modifier: Modifier = Modifier) {
    val breakpoint = rememberBreakpoint()
    var overflowMenuOpened by remember { mutableStateOf(false) }
    if (breakpoint > Breakpoint.MD) {
        SidePanel(modifier)
    } else {
        Box(modifier = Modifier.fillMaxSize().pointerEvents(PointerEvents.None).then(modifier)) {
            TopPanel(modifier = Modifier.pointerEvents(PointerEvents.Auto).align(Alignment.TopCenter)) {
                overflowMenuOpened = true
            }
            if (overflowMenuOpened)
                OverflowSidePanel(
                    modifier = Modifier.pointerEvents(PointerEvents.Auto).align(Alignment.CenterStart)
                ) {
                    overflowMenuOpened = false
                }
        }
    }
}