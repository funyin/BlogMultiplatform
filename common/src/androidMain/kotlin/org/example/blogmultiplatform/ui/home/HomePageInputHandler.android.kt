package org.example.blogmultiplatform.ui.home

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import kotlinx.coroutines.flow.collectLatest
import org.example.blogmultiplatform.data.MongoSyncRepo
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.data.repository.PostRepository
import org.example.blogmultiplatform.models.UiState

actual class HomePageInputHandler actual constructor(client: ApiClient) :
    InputHandler<
            HomePageContract.Inputs,
            HomePageContract.Events,
            HomePageContract.State> {


    private val postsRepository = PostRepository(client)

    override suspend fun InputHandlerScope<HomePageContract.Inputs, HomePageContract.Events, HomePageContract.State>.handleInput(
        input: HomePageContract.Inputs
    ) = when (input) {
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
    }

}