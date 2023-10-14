package org.example.blogmultiplatform.ui.search

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.data.repository.PostRepository
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState

class SearchPostsInputHandler(client: ApiClient) : InputHandler<
        SearchPostsContract.Inputs,
        SearchPostsContract.Events,
        SearchPostsContract.State> {

    val postRepository: PostRepository = PostRepository(client)
    override suspend fun InputHandlerScope<
            SearchPostsContract.Inputs,
            SearchPostsContract.Events,
            SearchPostsContract.State>.handleInput(
        input: SearchPostsContract.Inputs
    ): Unit = when (input) {

        is SearchPostsContract.Inputs.LoadData -> {
            updateState {
                it.copy(
                    resultState = if (it.resultState.isSuccess) it.resultState else UiState.Loading,
                    showMoreState = UiState.Loading
                )
            }
            val state = getCurrentState()
            sideJob("search") {
                val page = input.page
                postInput(
                    SearchPostsContract.Inputs.LoadData.LoadDataResponse(
                        postRepository.search(
                            state.searchValue.takeIf { it.isNotEmpty() },
                            state.category,
                            page = page,
                            size = input.size
                        ), page = page
                    )
                )
            }
        }


        is SearchPostsContract.Inputs.LoadData.LoadDataResponse -> {
            updateState {
                var state = it
                val resultState = state.resultState
                val newResultState = input.response
                if (input.page == 1)
                    state = state.copy(
                        resultState = newResultState,
                        showMoreState = newResultState,
                        page = if (newResultState.isSuccess)
                            input.page else state.page
                    )
                else
                    if (newResultState is UiState.Success) {
                        val newData = newResultState.data
                        val lastPage = newResultState.data.isEmpty()
                        state = state.copy(
                            resultState = UiState.Success(
                                data = mutableListOf<PostLight>().run {
                                    if (resultState is UiState.Success)
                                        this += resultState.data
                                    this += newData
                                    this
                                }),
                            page = input.page,
                            showMoreState = if (lastPage) UiState.Initial else newResultState
                        )
                    }

                state
            }

        }

        is SearchPostsContract.Inputs.Search -> {
            updateState { it.copy(searchValue = input.searchValue) }
        }
    }
}
