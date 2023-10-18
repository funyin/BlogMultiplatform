package org.example.blogmultiplatform.modules.home

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import org.example.blogmultiplatform.ui.home.HomePageContract

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
