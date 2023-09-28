package org.example.blogmultiplatform.pages.admin

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.blogmultiplatform.util.AuthGuarded

@Page
@Composable
fun HomePage(){
    AuthGuarded {
        SpanText("User Authenticated")
    }
}