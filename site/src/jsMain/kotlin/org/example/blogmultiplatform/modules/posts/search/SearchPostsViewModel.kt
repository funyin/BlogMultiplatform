package org.example.blogmultiplatform.modules.posts.search

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.plusAssign
import com.copperleaf.ballast.withViewModel
import io.ktor.client.engine.js.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.ui.search.SearchPostsContract
import org.example.blogmultiplatform.ui.search.SearchPostsInputHandler

@OptIn(FlowPreview::class)
class SearchPostsViewModel(
    coroutineScope: CoroutineScope,
    initialState: SearchPostsContract.State,
) : BasicViewModel<
        SearchPostsContract.Inputs,
        SearchPostsContract.Events,
        SearchPostsContract.State>(
    coroutineScope = coroutineScope,
    config = BallastViewModelConfiguration.Builder()
        .apply {
            this += LoggingInterceptor()
        }
        .withViewModel(
            inputHandler = SearchPostsInputHandler(ApiClient(Js.create())),
            initialState = initialState,
            name = "SearchPosts",
        )
        .build(),
    eventHandler = SearchPostsEventHandler()
) {
    init {
        coroutineScope.launch {
            observeStates().map { it.searchValue }
                .distinctUntilChanged()
                .debounce(500L).collectLatest {
                    trySend(SearchPostsContract.Inputs.LoadData(page = 1, size = 9))
                }
        }
    }
}
