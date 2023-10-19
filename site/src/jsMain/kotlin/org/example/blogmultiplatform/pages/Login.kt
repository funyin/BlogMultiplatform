package org.example.blogmultiplatform.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import kotlinx.coroutines.launch
import org.example.blogmultiplatform.components.widgets.AppButton
import org.example.blogmultiplatform.components.widgets.AuthRedirectGuard
import org.example.blogmultiplatform.components.widgets.CustomInputField
import org.example.blogmultiplatform.components.widgets.Toast
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
    var errorText by remember { mutableStateOf<String?>(null) }
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    val viewModel = remember { AuthViewModel(scope) }
    val uiState by viewModel.loginState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loginState.collect {
            it?.let {
                if (it.isSuccess) {
                    SessionManager.startSession(it.getOrThrow())
                    context.router.navigateTo(Res.Routes.adminHome)
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
                    modifier = Modifier.width(100.px).margin(bottom = 50.px).cursor(Cursor.Pointer)
                        .onClick {
                            context.router.navigateTo("/")
                        }
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
                AppButton(
                    text = "Sign in",
                    modifier = Modifier.margin(top = 20.px),
                    loading = uiState?.isSuccess != true && uiState?.isFailure != true && uiState != null
                ) {
                    if (userName.isNotEmpty() && password.isNotBlank()) {
                        viewModel.login(userName = userName, password = password)
                    } else {
                        scope.launch {
                            errorText = "Username and password are required"

                        }
                    }
                }
            }
        }
    }
    Toast(
        message = (uiState?.exceptionOrNull()?.message ?: errorText ?: "").toString(),
        show = uiState?.isFailure == true || errorText != null
    ) {
        errorText = null
        viewModel.clearLoginState()
    }
}

