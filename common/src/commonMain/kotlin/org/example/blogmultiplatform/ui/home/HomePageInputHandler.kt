package org.example.blogmultiplatform.ui.home

import com.copperleaf.ballast.InputHandler
import org.example.blogmultiplatform.data.api.core.ApiClient


expect class HomePageInputHandler(client: ApiClient) : InputHandler<
        HomePageContract.Inputs,
        HomePageContract.Events,
        HomePageContract.State> {

}
