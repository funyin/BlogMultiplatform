package org.example.blogmultiplatform.modules.home

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.plusAssign
import com.copperleaf.ballast.withViewModel
import io.ktor.client.engine.js.*
import kotlinx.coroutines.CoroutineScope
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.ui.home.HomePageContract
import org.example.blogmultiplatform.ui.home.HomePageEventHandler

class HomePageViewModel(
    coroutineScope: CoroutineScope,
) : BasicViewModel<
        HomePageContract.Inputs,
        HomePageContract.Events,
        HomePageContract.State>(
    coroutineScope = coroutineScope,
    config = BallastViewModelConfiguration.Builder()
        .apply {
            this += LoggingInterceptor()
        }
        .withViewModel(
            inputHandler = HomePageInputHandler(ApiClient(Js.create())),
            initialState = HomePageContract.State(),
            name = "HomePage",
        )
        .build(),
    eventHandler = HomePageEventHandler(),
)
