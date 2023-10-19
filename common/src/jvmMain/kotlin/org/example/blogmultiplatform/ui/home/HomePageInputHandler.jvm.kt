package org.example.blogmultiplatform.ui.home

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import org.example.blogmultiplatform.data.api.core.ApiClient



actual class HomePageInputHandler actual constructor(client: ApiClient) :
    InputHandler<
            HomePageContract.Inputs,
            HomePageContract.Events,
            HomePageContract.State> {
    override suspend fun InputHandlerScope<HomePageContract.Inputs, HomePageContract.Events, HomePageContract.State>.handleInput(
        input: HomePageContract.Inputs
    ) {
        TODO("Not yet implemented")
    }

}