package org.example.blogmultiplatform.modules.category

import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState

object CategoryPageContract {
    data class State(
        val category: Category,
        val state: UiState<List<PostLight>> = UiState.Initial,
    )

    sealed interface Inputs {
        object GetItems : Inputs {
            data class Response(val response: UiState<List<PostLight>>) : Inputs
        }
    }

    sealed interface Events {
    }
}
