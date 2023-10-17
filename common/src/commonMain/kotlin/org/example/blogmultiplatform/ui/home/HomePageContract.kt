package org.example.blogmultiplatform.ui.home

import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState

object HomePageContract {
    data class State(
        val mainPostsState: UiState<List<PostLight>> = UiState.Initial,
        val latestPostsState: UiState<List<PostLight>> = UiState.Initial,
        val sponsoredPostsState: UiState<List<PostLight>> = UiState.Initial,
        val popularPostsState: UiState<List<PostLight>> = UiState.Initial,
        val addSubscriberState: UiState<String> = UiState.Initial,
    )

    sealed interface Inputs {
        data object GetMainPosts : Inputs {
            data class Response(val response: UiState<List<PostLight>>) : Inputs
        }

        data object GetLatestPosts : Inputs {
            data class Response(val response: UiState<List<PostLight>>) : Inputs
        }

        data object GetSponsoredPosts : Inputs {
            data class Response(val response: UiState<List<PostLight>>) : Inputs
        }

        data object GetPopularPosts : Inputs {
            data class Response(val response: UiState<List<PostLight>>) : Inputs
        }

        data class AddSubscriber(val email: String) : Inputs {
            data class Response(val response: UiState<String>) : Inputs
        }
    }

    sealed interface Events {
    }
}
