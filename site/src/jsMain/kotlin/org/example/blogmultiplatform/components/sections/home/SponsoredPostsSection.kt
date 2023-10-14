package org.example.blogmultiplatform.components.sections.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.icons.fa.FaTag
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
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

val PostPreviewSponsoredVariant  = PostPreviewHeavyVariant.then(PostPreviewHorizontalVariant.then(PostPreviewBlueTitleVariant))
@Composable
fun SponsoredPostsSection(viewModel: HomePageViewModel) {
    val uiState by viewModel.observeStates().collectAsState()
    val breakpoint = rememberBreakpoint()
    val sponsoredPostsState = uiState.sponsoredPostsState
    val getPosts: () -> Unit = {
        viewModel.trySend(HomePageContract.Inputs.GetSponsoredPosts)
    }
    LaunchedEffect(Unit) {
        getPosts()
    }
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(AppColors.LightGrey.rgb),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(Res.Dimens.MAX_WIDTH(breakpoint).percent)
                .padding(top = 44.px, bottom = 50.px)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FaTag(size = IconSize.LG, modifier = Modifier.color(AppColors.Blue.rgb).margin(right = 8.px))
                SpanText(text = "Sponsored Posts", modifier = Modifier.color(AppColors.Blue.rgb).fontSize(18.px))
            }
            when (sponsoredPostsState) {
                is UiState.Error -> AppErrorView(text = sponsoredPostsState.errorMessage) {
                    getPosts()
                }

                UiState.Initial,
                UiState.Loading -> AppLoadingView()

                is UiState.Success -> {
                    if (sponsoredPostsState.data.isEmpty())
                        AppEmptyView(text = "No posts available")
                    else
                        SimpleGrid(
                            numColumns(base = 1, md = 2), modifier = Modifier.fillMaxWidth()
                                .margin(top = 30.px)
                                .rowGap(50.px)
                        ) {
                            sponsoredPostsState.data.forEach {
                                PostPreview(
                                    postLight = it,
                                    variant = PostPreviewSponsoredVariant,
                                    modifier = Modifier.minWidth(100.percent)
                                )
                            }
                        }
                }
            }
        }
    }
}