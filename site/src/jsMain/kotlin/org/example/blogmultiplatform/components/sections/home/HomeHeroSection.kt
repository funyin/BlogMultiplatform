package org.example.blogmultiplatform.components.sections.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.widgets.*
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.modules.home.HomePageViewModel
import org.example.blogmultiplatform.res.MAX_WIDTH
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.ui.home.HomePageContract
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vw


val PostPreviewHomePrimaryVariant = PostPreviewDarkVariant
    .then(PostPreviewHeavyVariant)
    .then(PostPreviewLargeVariant)

val PostPreviewHomeSecondaryVariant = PostPreviewDarkVariant
    .then(PostPreviewHeavyVariant)
    .then(PostPreviewHorizontalVariant)

@Composable
fun HomeHeroSection(viewModel: HomePageViewModel) {
    val breakpoint = rememberBreakpoint()
    val uiState by viewModel.observeStates().collectAsState()
    val getPosts = {
        viewModel.trySend(HomePageContract.Inputs.GetMainPosts)
    }
    LaunchedEffect(Unit) {
        getPosts()
    }
    val mainPostsState = uiState.mainPostsState
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Tertiary.rgb)
            .padding(top = 100.px, bottom = 60.px),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(Res.Dimens.MAX_WIDTH(breakpoint).percent)

        ) {
            when (mainPostsState) {
                is UiState.Error -> AppErrorView(text = mainPostsState.errorMessage) {
                    getPosts()
                }

                UiState.Initial,
                UiState.Loading -> AppLoadingView()

                is UiState.Success -> {
                    SimpleGrid(
                        numColumns = numColumns(base = 1, md = 3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .columnGap(50.px)
                    ) {
                        PostPreview(
                            modifier = Modifier
                                .fillMaxWidth()
                                .thenIf(breakpoint >= Breakpoint.MD, Modifier.minWidth(40.vw))
                                .minHeight(660.px)
                                .thenIf(breakpoint >= Breakpoint.MD, Modifier.styleModifier {
                                    property("grid-column", "span 2")
                                }),
                            postLight = mainPostsState.data.first(),
                            variant = PostPreviewHomePrimaryVariant
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .thenIf(breakpoint > Breakpoint.SM, Modifier.maxWidth(32.vw))
                                .minWidth(100.percent)
                                .gap(50.px)
                        ) {
                            mainPostsState.data.drop(1).forEach {
                                PostPreview(
                                    postLight = it,
                                    variant = PostPreviewHomeSecondaryVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}