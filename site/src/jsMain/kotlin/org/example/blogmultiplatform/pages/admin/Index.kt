package org.example.blogmultiplatform.pages.admin

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import io.ktor.client.engine.js.*
import org.example.blogmultiplatform.components.layouts.AdminPageLayout
import org.example.blogmultiplatform.components.widgets.AddButton
import org.example.blogmultiplatform.components.widgets.Spinner
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.core.SessionManager
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.models.RandomJoke
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.jokeAPI_KEY
import org.example.blogmultiplatform.res.jokesBaseUrl
import org.example.blogmultiplatform.res.laugh
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import kotlin.js.Date

@Page
@Composable
fun HomePage() {
    AdminPageLayout {
        JokeContent()
        AddButton()
    }
}

@Composable
private fun JokeContent() {
    var joke by remember { mutableStateOf(SessionManager.lastFetchedJoke) }
    val loadingJoke by remember(joke) {
        mutableStateOf(joke == null)
    }
    LaunchedEffect(Unit) {
        val lastFetchDate = SessionManager.jokeLastFetchDate
        val today = Date()
        if (today.getDate() != lastFetchDate?.getDate()) {
            joke = try {
                ApiClient(Js.create()).get(
                    Res.Strings.jokesBaseUrl, parameters = mapOf(
                        "api-key" to Res.Strings.jokeAPI_KEY,
                        "max-length" to "180",
                    )
                )
            } catch (e: Exception) {
                RandomJoke(id = -1, joke = e.message.toString())
            }
            if (joke?.id != -1)
                SessionManager.lastFetchedJoke = joke
        }
    }
    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().maxHeight(100.vh), contentAlignment = Alignment.Center) {
        if (loadingJoke)
            Spinner()
        else
            Column(
                modifier = Modifier.fillMaxSize().padding(topBottom = 50.px),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(src = Res.Images.laugh, modifier = Modifier.size(150.px).margin(bottom = 50.px))
                SpanText(
                    text = "${joke?.joke}", modifier = Modifier.fillMaxWidth(60.percent)
                        .textAlign(TextAlign.Center).fontSize(28.px).color(AppColors.Secondary.rgb)
                        .fontWeight(FontWeight.Bold)
                        .fillMaxWidth(60.percent)
                        .fontFamily(Res.Strings.FONT_FAMILY)
                        .margin(bottom = 40.px)
                )
            }
    }
}

