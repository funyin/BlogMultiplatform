package org.example.blogmultiplatform.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.blogmultiplatform.components.widgets.AppButton
import org.example.blogmultiplatform.components.widgets.AuthRedirectGuard
import org.example.blogmultiplatform.components.widgets.CustomInputField
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.core.SessionManager
import org.example.blogmultiplatform.modules.auth.AuthViewModel
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.adminHome
import org.example.blogmultiplatform.res.logo
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px

@Page(routeOverride = "login")
@Composable
fun LoginPage() {
    var errorText by remember { mutableStateOf(" ") }
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    val viewModel = remember { AuthViewModel(scope) }
    LaunchedEffect(Unit) {
        viewModel.loginState.collect {
            if (it == null) {
                errorText = ""
                return@collect
            }
            if (it.isSuccess) {
                SessionManager.startSession(it.getOrThrow())
                errorText = " "
                context.router.navigateTo(Res.Routes.adminHome)
            }
            if (it.isFailure) {
                it.exceptionOrNull()?.message?.let { error ->
                    errorText = error
                }
            }
        }
    }
    AuthRedirectGuard {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.padding(leftRight = 50.px, top = 80.px, bottom = 24.px)
                    .background(AppColors.LightGrey.rgb),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    Res.Images.logo,
                    desc = "LogoImage",
                    modifier = Modifier.width(100.px).margin(bottom = 50.px)
                )
                CustomInputField(
                    placeholder = "Username",
                    value = userName,
                    inputType = InputType.Text,
                    name = "username",
                    modifier = Modifier.margin(top = 12.px)
                ) {
                    userName = it.trim()
                }
                CustomInputField(
                    placeholder = "Password",
                    value = password,
                    name = "password",
                    inputType = InputType.Password,
                    modifier = Modifier.margin(top = 12.px)
                ) {
                    password = it.trim()
                }
                AppButton(text = "Sign in", modifier = Modifier.margin(top = 20.px)) {
                    if (userName.isNotEmpty() && password.isNotBlank()) {
                        errorText = "loading"
                        viewModel.login(userName = userName, password = password)
                    } else {
                        scope.launch {
                            errorText = "Input fields are empty"
                            delay(3000)
                            errorText = " "
                        }
                    }
                }

                SpanText(
                    text = errorText,
                    modifier = Modifier
                        .fontFamily(Res.Strings.FONT_FAMILY)
                        .margin(topBottom = 24.px).width(350.px).color(Colors.Red).textAlign(
                            TextAlign.Center
                        )
                )
            }
        }
    }
}

