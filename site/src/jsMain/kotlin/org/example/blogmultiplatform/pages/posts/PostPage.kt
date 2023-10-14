package org.example.blogmultiplatform.pages.posts

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.widgets.AppErrorView
import org.example.blogmultiplatform.components.widgets.AppLoadingView
import org.example.blogmultiplatform.components.widgets.MarkDownContent
import org.example.blogmultiplatform.core.AppColors
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.modules.posts.post.PostPageViewModel
import org.example.blogmultiplatform.pages.SitePageLayout
import org.example.blogmultiplatform.res.MAX_WIDTH
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.ui.post.PostPageContract
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import kotlin.js.Date

@Page("{postId}")
@Composable
fun PostPage() {
    val breakpoint = rememberBreakpoint()
    val pageContext = rememberPageContext()
    val postId = pageContext.route.params["postId"]!!
    val scope = rememberCoroutineScope()
    val viewModel =
        remember(postId) { PostPageViewModel(scope, initialState = PostPageContract.State(postId = postId)) }
    val uiState by viewModel.observeStates().collectAsState()
    val postState = uiState.postsState
    val getPost = { viewModel.trySend(PostPageContract.Inputs.GetData(postId)) }
    LaunchedEffect(postId) {
        getPost()
    }
    SitePageLayout {
        Column(
            modifier = Modifier.fillMaxWidth(Res.Dimens.MAX_WIDTH(breakpoint = breakpoint).percent).maxWidth(800.px)
                .padding(top = 40.px, bottom = 200.px)
        ) {
            when (postState) {
                is UiState.Error -> AppErrorView(text = postState.errorMessage) {
                    getPost()
                }

                UiState.Initial,
                UiState.Loading -> AppLoadingView()

                is UiState.Success -> {
                    val post = postState.data
                    SpanText(
                        text = Date(post.date).toLocaleDateString(),
                        modifier = Modifier.color(AppColors.HalfBlack.rgb).fontSize(14.px)
                    )
                    SpanText(
                        text = post.title,
                        modifier = Modifier.fontSize(50.px).fontWeight(FontWeight.Bold).margin(top = 6.px)
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(600.px)
                            .background(AppColors.LightGrey.rgb)
                            .margin(top = 12.px)
                    ) {
                        Image(
                            src = post.thumbnail,
                            modifier = Modifier.fillMaxSize()
                                .objectFit(ObjectFit.Cover)
                        )
                    }
                    SpanText(text = post.subtitle, modifier = Modifier.fontSize(24.px).margin(top = 40.px))
                    MarkDownContent(modifier = Modifier.fillMaxWidth().margin(top = 40.px), markDown = post.content)
                }
            }
        }
    }
}