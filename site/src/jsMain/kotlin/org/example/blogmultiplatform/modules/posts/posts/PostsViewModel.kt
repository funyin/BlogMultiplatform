package org.example.blogmultiplatform.modules.posts.posts

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.modules.posts.PostRepository

class PostsViewModel(private val viewModelScope: CoroutineScope) {
    private val _uiState = MutableStateFlow(PostsUIState())
    val uiState = _uiState.asStateFlow()
    private val repository = PostRepository()

    fun postState(state: PostsUIState) {
        _uiState.update { state }
    }

    private fun getPosts(page: Int, size: Int): Flow<UiState<List<PostLight>>> = channelFlow {
        send(UiState.Loading)
        if (page == 1)
            postState(_uiState.value.copy(postsState = UiState.Loading))
        val response = repository.posts(page, size)
        val currentState = _uiState.value
        val currentPostsState = currentState.postsState
        postState(
            _uiState.value.copy(
                postsState = if (currentPostsState.isSuccess && response.isSuccess) {
                    val list = currentPostsState.getData.toMutableList()
                    list.addAll(response.getData)
                    UiState.Success(data = list.toList())
                } else response,
                page = if (response is UiState.Success && response.data.isNotEmpty()) page else currentState.page
            )
        )
        send(response)
    }

    fun showMore(size: Int = 10) {
        viewModelScope.launch {
            getPosts(_uiState.value.page + 1, size).collectLatest {
                postState(
                    _uiState.value.copy(
                        showMoreState = if (it.isSuccess) {
                            val lastPage = it.getData.size < size
                            if (lastPage) UiState.Initial else it
                        } else it
                    )
                )
            }
        }
    }

    fun deletePosts() {
        val selectedPosts = _uiState.value.selectedPosts
        if (selectedPosts.isEmpty()) {
            postState(_uiState.value.copy(deletePostsState = UiState.Error("Select posts to delete")))
            return
        }
        viewModelScope.launch {
            repository.deletePosts(items = selectedPosts).collect {
                postState(_uiState.value.copy(deletePostsState = it))
                if (it.isSuccess) {
                    postState(_uiState.value.copy(page = 0, selectedPosts = emptyList(), selectMode = false))
                    showMore()
                }
            }
        }
    }
}