package org.example.blogmultiplatform.components.sections.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.widgets.AppErrorView
import org.example.blogmultiplatform.components.widgets.AppLoadingView
import org.example.blogmultiplatform.components.widgets.PostPreview
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.modules.home.HomePageViewModel
import org.example.blogmultiplatform.res.MAX_WIDTH
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.ui.home.HomePageContract.Inputs
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun LatestPostsSection(viewModel: HomePageViewModel) {
    val uiState by viewModel.observeStates().collectAsState()
    val postsState = uiState.latestPostsState
    val getPosts: () -> Unit = { viewModel.trySend(Inputs.GetLatestPosts) }
    LaunchedEffect(Unit) {
        getPosts()
    }
    PostsGrid(
        postsState = postsState, getPosts = getPosts,
        title = "Latest Posts",
        modifier = Modifier.margin(top = 90.px, bottom = 80.px)
    )
}

@Composable
fun PostsGrid(
    modifier: Modifier = Modifier,
    postsState: UiState<List<PostLight>>,
    title: String,
    getPosts: () -> Unit
) {

    val breakpoint = rememberBreakpoint()

    Column(
        modifier = modifier
            .fillMaxWidth(Res.Dimens.MAX_WIDTH(breakpoint).percent)

    ) {
        SpanText(
            text = title,
            modifier = Modifier.fontSize(18.px).color(Colors.Black).margin(bottom = 24.px),
        )
        when (postsState) {
            is UiState.Error -> AppErrorView(text = postsState.errorMessage) {
                getPosts()
            }

            UiState.Initial,
            UiState.Loading -> AppLoadingView()

            is UiState.Success -> {
                SimpleGrid(
                    numColumns = numColumns(base = 1, sm = 2, md = 3, lg = 4),
                    modifier = Modifier.fillMaxWidth().gap(43.px)
                ) {
                    postsState.data.forEach {
                        PostPreview(postLight = it)
                    }
                }
            }
        }
    }
}