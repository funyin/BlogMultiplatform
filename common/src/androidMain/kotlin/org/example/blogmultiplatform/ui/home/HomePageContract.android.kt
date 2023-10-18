package org.example.blogmultiplatform.ui.home

import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState

actual object HomePageContract {
    actual data class State(
        val allPosts: UiState<List<PostLight>> = UiState.Initial
    )

    actual sealed interface Inputs {
        data object GetAllPosts : Inputs {
            data class Response(val response: UiState<List<PostLight>>) : Inputs
        }
    }

    actual sealed interface Events
}