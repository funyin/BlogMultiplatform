package org.example.blogmultiplatform.modules.posts

import com.copperleaf.ballast.*
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.JsConsoleLogger
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.savedstate.BallastSavedStateInterceptor
import kotlinx.coroutines.CoroutineScope
import org.example.blogmultiplatform.core.SessionManager
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.Events
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.Inputs
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.State
import org.example.blogmultiplatform.ui.createPost.CreatePostSavedStateAdapter
import org.example.blogmultiplatform.ui.createPost.CreatePostScreenInputHandler

class CreatePostViewModel(
    eventHandler: EventHandler<Inputs, Events, State>,
    coroutineScope: CoroutineScope
) : BasicViewModel<Inputs, Events, State>(
    BallastViewModelConfiguration.Builder()
        .apply {
            this += LoggingInterceptor()
            this += BallastSavedStateInterceptor(
                adapter = CreatePostSavedStateAdapter(object : CreatePostSavedStateAdapter.Prefs {
                    override var stateData: String
                        get() = SessionManager.getCreatePostState()
                        set(value) {
                            SessionManager.setCreatePostState(value)
                        }

                })
            )
            logger = {
                JsConsoleLogger(it)
            }
        }
        .withViewModel(State(), inputHandler = CreatePostScreenInputHandler())
        .build(), eventHandler, coroutineScope
) {
}