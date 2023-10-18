package org.example.blogmultiplatform.ui.home

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.data.repository.PostRepository
import org.example.blogmultiplatform.data.repository.SubscribersRepository
import org.example.blogmultiplatform.models.UiState

expect class HomePageInputHandler(client: ApiClient) : InputHandler<
        HomePageContract.Inputs,
        HomePageContract.Events,
        HomePageContract.State> {

}
