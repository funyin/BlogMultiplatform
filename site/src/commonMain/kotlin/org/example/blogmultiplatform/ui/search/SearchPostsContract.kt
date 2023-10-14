package org.example.blogmultiplatform.ui.search

import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState

object SearchPostsContract {
    data class State(
        val searchValue: String = "",
        val category: Category? = null,
        val page: Int = 0,
        val resultState: UiState<List<PostLight>> = UiState.Initial,
        val showMoreState: UiState<List<PostLight>> = UiState.Initial
    )

    sealed interface Inputs {
        data class Search(val searchValue: String) : Inputs
        data class LoadData(val page: Int, val size: Int) : Inputs {
            data class LoadDataResponse(val response: UiState<List<PostLight>>, val page: Int) : Inputs
        }
    }

    sealed interface Events {
        data object NavigateToPost : Events
    }
}
