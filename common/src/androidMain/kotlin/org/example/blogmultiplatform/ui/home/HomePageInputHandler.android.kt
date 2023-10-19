package org.example.blogmultiplatform.ui.home

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.postInput
import kotlinx.coroutines.flow.collectLatest
import org.example.blogmultiplatform.data.MongoSyncRepo
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.models.UiState

actual class HomePageInputHandler actual constructor(client: ApiClient) :
    InputHandler<
            HomePageContract.Inputs,
            HomePageContract.Events,
            HomePageContract.State> {

    override suspend fun InputHandlerScope<HomePageContract.Inputs, HomePageContract.Events, HomePageContract.State>.handleInput(
        input: HomePageContract.Inputs
    ) {
        when (input) {
            HomePageContract.Inputs.GetAllPosts -> {
                updateState { it.copy(allPosts = UiState.Loading) }
                sideJob("allPosts") {
                    MongoSyncRepo.init()
                    val mongoSyncRepo = MongoSyncRepo()
                    mongoSyncRepo.readAllPost().collectLatest {
                        postInput(
                            HomePageContract.Inputs.GetAllPosts.Response(
                                response = it
                            )
                        )
                    }
                }
            }

            is HomePageContract.Inputs.GetAllPosts.Response -> {
                updateState { it.copy(allPosts = input.response) }
            }

            is HomePageContract.Inputs.SearchPostsPosts -> {
                updateState { it.copy(searchResponse = UiState.Loading, searchValue = input.title) }
                sideJob("searchPosts") {
                    val mongoSyncRepo = MongoSyncRepo()
                    mongoSyncRepo.searchByTitle(input.title).collectLatest {
                        postInput(
                            HomePageContract.Inputs.SearchPostsPosts.Response(
                                response = it
                            )
                        )
                    }
                }
            }

            is HomePageContract.Inputs.SearchPostsPosts.Response -> {
                updateState { it.copy(searchResponse = input.response) }
            }

            is HomePageContract.Inputs.SearchChanged -> {
                updateState { it.copy(searchValue = input.value) }
            }

            is HomePageContract.Inputs.ShowSearchBar -> {
                updateState { it.copy(showSearchBar = input.show) }
                if (!input.show) {
                    postInput(HomePageContract.Inputs.SearchChanged(""))
                    postInput(HomePageContract.Inputs.SearchPostsPosts.Response(UiState.Initial))
                }
            }
        }
    }

}