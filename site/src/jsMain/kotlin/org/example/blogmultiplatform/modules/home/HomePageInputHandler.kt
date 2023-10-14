package org.example.blogmultiplatform.modules.home

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.data.repository.PostRepository
import org.example.blogmultiplatform.data.repository.SubscribersRepository
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.ui.home.HomePageContract

class HomePageInputHandler(client: ApiClient) : InputHandler<
        HomePageContract.Inputs,
        HomePageContract.Events,
        HomePageContract.State> {

    private val postRepository = PostRepository(client)
    private val subscribersRepository = SubscribersRepository(client)

    override suspend fun InputHandlerScope<
            HomePageContract.Inputs,
            HomePageContract.Events,
            HomePageContract.State>.handleInput(
        input: HomePageContract.Inputs
    ): Unit = when (input) {

        HomePageContract.Inputs.GetMainPosts -> {
            updateState { it.copy(mainPostsState = UiState.Loading) }
            sideJob("getMainPosts") {
                postInput(HomePageContract.Inputs.GetMainPosts.Response(postRepository.mainPosts()))
            }
        }

        is HomePageContract.Inputs.GetMainPosts.Response -> {
            updateState { it.copy(mainPostsState = input.response) }
        }

        HomePageContract.Inputs.GetLatestPosts -> {
            updateState { it.copy(latestPostsState = UiState.Loading) }
            sideJob("getLatestPosts") {
                postInput(HomePageContract.Inputs.GetLatestPosts.Response(postRepository.latestPosts(1, 8)))
            }
        }

        is HomePageContract.Inputs.GetLatestPosts.Response -> {
            updateState { it.copy(latestPostsState = input.response) }
        }

        HomePageContract.Inputs.GetSponsoredPosts -> {
            updateState { it.copy(sponsoredPostsState = UiState.Loading) }
            sideJob("getSponsoredPosts") {
                postInput(HomePageContract.Inputs.GetSponsoredPosts.Response(postRepository.sponsoredPosts()))
            }
        }

        is HomePageContract.Inputs.GetSponsoredPosts.Response -> {
            updateState { it.copy(sponsoredPostsState = input.response) }
        }

        HomePageContract.Inputs.GetPopularPosts -> {
            updateState { it.copy(popularPostsState = UiState.Loading) }
            sideJob("getPopularPosts") {
                postInput(HomePageContract.Inputs.GetPopularPosts.Response(postRepository.popularPosts(1, 8)))
            }
        }

        is HomePageContract.Inputs.GetPopularPosts.Response -> {
            updateState { it.copy(popularPostsState = input.response) }
        }

        is HomePageContract.Inputs.AddSubscriber -> {
            updateState { it.copy(addSubscriberState = UiState.Loading) }
            sideJob("addSubscriber") {
                postInput(HomePageContract.Inputs.AddSubscriber.Response(subscribersRepository.addSubscriber(input.email)))
            }
        }

        is HomePageContract.Inputs.AddSubscriber.Response -> {
            updateState { it.copy(addSubscriberState = input.response) }
        }
    }
}
