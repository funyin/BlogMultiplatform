package org.example.blogmultiplatform.modules.posts.posts

import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState

data class PostsUIState(
    val searchValue: String = "",
    val selectMode: Boolean = false,
    val postsState: UiState<List<PostLight>> = UiState.Initial,
    val showMoreState: UiState<List<PostLight>> = UiState.Initial,
    val page: Int = 0,
    val selectedPosts: List<String> = emptyList(),
    val deletePostsState: UiState<Boolean> = UiState.Initial,
    val showConfirmDeleteDialog: Boolean = false
)
