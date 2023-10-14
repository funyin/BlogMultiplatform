package org.example.blogmultiplatform.components.sections.home

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.widgets.*
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.modules.home.HomePageViewModel
import org.example.blogmultiplatform.res.MAX_WIDTH
import org.example.blogmultiplatform.res.PAGE_WIDTH
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.ui.home.HomePageContract
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun NewsLetterSection(viewModel: HomePageViewModel) {
    val uiState by viewModel.observeStates().collectAsState()
    val addSubscriberState = uiState.addSubscriberState
    val breakpoint = rememberBreakpoint()
    var email by remember { mutableStateOf("") }
    LaunchedEffect(addSubscriberState) {
        if (addSubscriberState.isSuccess) {
            email = ""
        }
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(Res.Dimens.MAX_WIDTH(breakpoint).percent)
                .maxWidth(Res.Dimens.PAGE_WIDTH.px)
                .padding(topBottom = 250.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpanText(
                "Don’t miss any New Post.\nSign up to our Newsletter!", modifier = Modifier.textAlign(TextAlign.Center)
                    .fontSize(34.px)
                    .fontWeight(FontWeight.Bold)
                    .whiteSpace(WhiteSpace.PreWrap)
            )
            SpanText(
                text = "Keep up with the latest news and blogs.", modifier = Modifier
                    .margin(top = 6.px)
                    .textAlign(TextAlign.Center)
                    .fontSize(18.px)
                    .color(Color.rgb(0x646464))

            )
            SimpleGrid(
                numColumns = numColumns(base = 1, md = 2),
                modifier = Modifier.margin(top = 40.px)
                    .maxWidth(522.px)
                    .gap(20.px)
                    .fillMaxWidth()
            ) {
                CustomInputField(
                    value = email, inputType = InputType.Email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .thenIf(breakpoint >= Breakpoint.SM, Modifier.minWidth(320.px))
                        .thenIf(breakpoint < Breakpoint.SM, Modifier.minWidth(100.percent)),
                    placeholder = "Your Email Address",
                    variant = CustomInputGreyVariant.then(CustomInputRoundedVariant)
                ) {
                    email = it
                }
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    AppButton(
                        text = "Subscribe",
                        modifier = Modifier.fillMaxWidth().maxWidth(182.px),
                        loading = addSubscriberState.isLoading,
                        variant = AppButtonRoundedVariant
                    ) {
                        if (email.isEmpty()) {
                            viewModel.trySend(HomePageContract.Inputs.AddSubscriber.Response(UiState.Error("Email is required")))
                        } else
                            viewModel.trySend(HomePageContract.Inputs.AddSubscriber(email))

                    }
                }
            }
        }
    }
    Toast(
        message = if(addSubscriberState.isSuccess) addSubscriberState.getData else addSubscriberState.getMessage ?: "",
        show = addSubscriberState.isError || addSubscriberState.isSuccess
    ) {
        viewModel.trySend(HomePageContract.Inputs.AddSubscriber.Response(UiState.Initial))
    }
}