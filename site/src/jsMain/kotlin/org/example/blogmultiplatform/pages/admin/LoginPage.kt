package org.example.blogmultiplatform.pages.admin

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.localStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.models.Theme
import org.example.blogmultiplatform.modules.auth.AuthViewModel
import org.example.blogmultiplatform.styles.LoginInputStyle
import org.example.blogmultiplatform.util.Res
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.set

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
                localStorage[Res.Strings.StoreKeys.user] = Json.encodeToString(it.getOrThrow())
                errorText = " "
                context.router.navigateTo("/admin")
            }
            if (it.isFailure) {
                it.exceptionOrNull()?.message?.let { error ->
                    errorText = error
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(leftRight = 50.px, top = 80.px, bottom = 24.px)
                .background(Theme.LightGrey.rgb),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                Res.Images.logo,
                desc = "LogoImage",
                modifier = Modifier.width(100.px).margin(bottom = 50.px)
            )
            Input(
                InputType.Email, attrs = LoginInputStyle.toModifier()
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .margin(bottom = 12.px)
                    .background(Colors.White)
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .fontSize(14.px)
                    .toAttrs {
                        placeholder("Username")
                        onChange {
                            userName = it.value.trim()
                        }
                    }
            )
            Input(
                InputType.Password, attrs = LoginInputStyle.toModifier()
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .background(Colors.White)
                    .margin(bottom = 20.px)
                    .fontSize(14.px)
                    .toAttrs {
                        placeholder("Password")
                        onChange {
                            password = it.value.trim()
                        }
                    }
            )
            Button(
                attrs = Modifier
                    .width(350.px)
                    .height(54.px)
                    .backgroundColor(Theme.Primary.rgb)
                    .color(Colors.White)
                    .border(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    ).outline(
                        width = 0.px,
                        style = LineStyle.None,
                        color = Colors.Transparent
                    )
                    .borderRadius(4.px)
                    .fontWeight(FontWeight.Medium)
                    .fontFamily(Res.Strings.FONT_FAMILY)
                    .fontSize(14.px)
                    .cursor(Cursor.Pointer)
                    .onClick {
                        if (userName.isNotEmpty() && password.isNotEmpty()) {
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
                    .toAttrs()
            ) {
                SpanText(text = "Sign in")
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