package org.example.blogmultiplatform.ui.post

import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.UiState

object PostPageContract {
    data class State(
        val postId: String,
        val postsState: UiState<Post> = UiState.Initial,
    )

    sealed interface Inputs {
        data class GetData(val postId: String) : Inputs {
            data class State(val state: UiState<Post>) : Inputs
        }
    }

    sealed interface Events {

    }
}
