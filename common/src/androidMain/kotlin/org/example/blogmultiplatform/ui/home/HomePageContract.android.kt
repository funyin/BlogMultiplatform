package org.example.blogmultiplatform.ui.home

import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState

actual object HomePageContract {
    actual data class State(
        val allPosts: UiState<List<PostLight>> = UiState.Initial,
        val searchResponse: UiState<List<PostLight>> = UiState.Initial,
        val showSearchBar: Boolean = false,
        val searchValue: String = "",
    )

    actual sealed interface Inputs {
        data object GetAllPosts : Inputs {
            data class Response(val response: UiState<List<PostLight>>) : Inputs
        }

        data class SearchPostsPosts(val title: String) : Inputs {
            data class Response(val response: UiState<List<PostLight>>) : Inputs
        }

        data class ShowSearchBar(val show: Boolean) : Inputs
        data class SearchChanged(val value: String) : Inputs
    }

    actual sealed interface Events
}