package org.example.blogmultiplatform.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.rememberPageContext
import kotlinx.browser.localStorage
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.models.User
import org.example.blogmultiplatform.modules.auth.AuthRepository
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.login
import org.w3c.dom.get

@Composable
fun AuthGuard(content: @Composable () -> Unit) {
    val context = rememberPageContext()
    val user = remember {
        localStorage[Res.Strings.StoreKeys.user]?.let { Json.decodeFromString<User>(it) }
    }
    var authenticated by remember { mutableStateOf(user != null) }
    LaunchedEffect(Unit) {
        try {
            authenticated = user?.let {
                AuthRepository().checkUserId(it.id)
            } ?: false
            if (!authenticated) {
                context.router.navigateTo(Res.Routes.login)
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    if (authenticated)
        content()
    else
        Spinner()
}