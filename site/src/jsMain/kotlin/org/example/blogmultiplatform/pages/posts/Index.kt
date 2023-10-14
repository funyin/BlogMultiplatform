package org.example.blogmultiplatform.pages.posts

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.example.blogmultiplatform.components.widgets.AppEmptyView
import org.example.blogmultiplatform.components.widgets.AppErrorView
import org.example.blogmultiplatform.components.widgets.AppLoadingView
import org.example.blogmultiplatform.components.widgets.PostPreview
import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.modules.posts.search.SearchPostsViewModel
import org.example.blogmultiplatform.pages.SitePageLayout
import org.example.blogmultiplatform.pages.admin.posts.ShowMoreButton
import org.example.blogmultiplatform.res.MAX_WIDTH
import org.example.blogmultiplatform.res.PAGE_WIDTH
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.post
import org.example.blogmultiplatform.ui.search.SearchPostsContract
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page("/posts")
@Composable
fun ClientPostsPage() {
    val context = rememberPageContext()
    val category = context.route.params["category"]
    val scope = rememberCoroutineScope()
    val viewModel = remember(category) {
        SearchPostsViewModel(
            scope,
            SearchPostsContract.State(category = Category.entries.firstOrNull { it.name == category })
        )
    }
    val uiState by viewModel.observeStates().collectAsState()
    val resultsState = uiState.resultState
    val getData = {
        viewModel.trySend(SearchPostsContract.Inputs.LoadData(page = 1, size = 9))
    }
    val breakpoint = rememberBreakpoint()
    SitePageLayout {
        LaunchedEffect(it) {
            viewModel.trySend(SearchPostsContract.Inputs.Search(it))
        }
        Column(
            modifier = Modifier.fillMaxWidth(Res.Dimens.MAX_WIDTH(breakpoint).percent)
                .padding(top = 100.px, bottom = 200.px).maxWidth(Res.Dimens.PAGE_WIDTH.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpanText(
                text = category ?: "Search${
                    it.run {
                        if (isNotEmpty()) ": $this"
                        else
                            ""
                    }
                }", modifier = Modifier.fontSize(36.px).margin(bottom = 40.px)
            )
            when (resultsState) {
                is UiState.Error -> AppErrorView(text = resultsState.errorMessage) {
                    getData()
                }

                UiState.Initial,
                UiState.Loading -> AppLoadingView()

                is UiState.Success -> {
                    if (resultsState.data.isEmpty()) {
                        AppEmptyView(text = "Not Posts Found")
                    } else {
                        SimpleGrid(
                            numColumns(base = 1, md = 3, sm = 2),
                            modifier = Modifier.gap(50.px).fillMaxWidth()
                        ) {
                            resultsState.data.forEach {
                                PostPreview(postLight = it){
                                    context.router.navigateTo(Res.Routes.post(it.id))
                                }
                            }
                        }
                        ShowMoreButton(uiState.showMoreState) {
                            viewModel.trySend(SearchPostsContract.Inputs.LoadData(page = uiState.page + 1, size = 9))
                        }
                    }
                }
            }
        }


    }
}