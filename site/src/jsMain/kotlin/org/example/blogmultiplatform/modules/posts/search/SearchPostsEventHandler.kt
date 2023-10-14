package org.example.blogmultiplatform.modules.posts.search

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import org.example.blogmultiplatform.ui.search.SearchPostsContract

class SearchPostsEventHandler : EventHandler<
        SearchPostsContract.Inputs,
        SearchPostsContract.Events,
        SearchPostsContract.State> {
    override suspend fun EventHandlerScope<
            SearchPostsContract.Inputs,
            SearchPostsContract.Events,
            SearchPostsContract.State>.handleEvent(
        event: SearchPostsContract.Events
    ): Unit = when (event) {
        is SearchPostsContract.Events.NavigateToPost -> {

        }
    }
}
