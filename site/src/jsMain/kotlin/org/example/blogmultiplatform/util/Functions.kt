package org.example.blogmultiplatform.util

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.localStorage
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.models.User
import org.example.blogmultiplatform.modules.auth.AuthRepository
import org.w3c.dom.get

@Composable
fun AuthGuarded(content: @Composable () -> Unit) {
    val context = rememberPageContext()
    var authenticated by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        try {
            val user = localStorage[Res.Strings.StoreKeys.user]?.let { Json.decodeFromString<User>(it) }
            authenticated = user?.let {
                AuthRepository().checkUserId(it.id)
            } ?: false

            if (!authenticated) {
                context.router.navigateTo(Res.Strings.Routes.login)
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    if (authenticated)
        content()
    else
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            SpanText("Loading...")
        }
}