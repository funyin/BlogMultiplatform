package org.example.blogmultiplatform.ui.home

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope

class HomePageEventHandler : EventHandler<
        HomePageContract.Inputs,
        HomePageContract.Events,
        HomePageContract.State> {
    override suspend fun EventHandlerScope<
            HomePageContract.Inputs,
            HomePageContract.Events,
            HomePageContract.State>.handleEvent(
        event: HomePageContract.Events
    ): Unit = when {
        else -> {

        }
    }
}
