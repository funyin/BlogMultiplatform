package org.example.blogmultiplatform.modules.home

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.AndroidViewModel
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.plusAssign
import com.copperleaf.ballast.withViewModel
import io.ktor.client.engine.android.*
import kotlinx.coroutines.CoroutineScope
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.ui.home.HomePageContract
import org.example.blogmultiplatform.ui.home.HomePageInputHandler

class HomePageViewModel(coroutineScope: CoroutineScope) : AndroidViewModel<
        HomePageContract.Inputs,
        HomePageContract.Events,
        HomePageContract.State>(
    config = BallastViewModelConfiguration.Builder()
        .apply {
            this += LoggingInterceptor()
        }
        .withViewModel(
            inputHandler = HomePageInputHandler(ApiClient(Android.create())),
            initialState = HomePageContract.State(),
            name = "HomePage",
        )
        .build(),
    coroutineScope = coroutineScope
)
