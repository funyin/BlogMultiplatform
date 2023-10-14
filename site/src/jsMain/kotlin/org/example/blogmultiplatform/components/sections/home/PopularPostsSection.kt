package org.example.blogmultiplatform.components.sections.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.core.rememberPageContext
import org.example.blogmultiplatform.modules.home.HomePageViewModel
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.post
import org.example.blogmultiplatform.ui.home.HomePageContract
import org.jetbrains.compose.web.css.px

@Composable
fun PopularPostsSection(viewModel: HomePageViewModel) {
    val uiState by viewModel.observeStates().collectAsState()
    val postsState = uiState.popularPostsState
    val context = rememberPageContext()
    val getPosts: () -> Unit = { viewModel.trySend(HomePageContract.Inputs.GetPopularPosts) }
    LaunchedEffect(Unit) {
        getPosts()
    }
    PostsGrid(
        postsState = postsState, getPosts = getPosts,
        title = "Popular Posts",
        modifier = Modifier.margin(top = 90.px)
    ){
        context.router.navigateTo(Res.Routes.post(it.id))
    }
}