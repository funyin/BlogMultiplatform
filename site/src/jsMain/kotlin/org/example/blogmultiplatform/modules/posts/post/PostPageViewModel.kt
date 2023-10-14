package org.example.blogmultiplatform.modules.posts.post

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.plusAssign
import com.copperleaf.ballast.withViewModel
import io.ktor.client.engine.js.*
import kotlinx.coroutines.CoroutineScope
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.ui.post.PostPageContract
import org.example.blogmultiplatform.ui.post.PostPageInputHandler

class PostPageViewModel(
    coroutineScope: CoroutineScope,
    initialState : PostPageContract.State
) : BasicViewModel<
        PostPageContract.Inputs,
        PostPageContract.Events,
        PostPageContract.State>(
    coroutineScope = coroutineScope,
    config = BallastViewModelConfiguration.Builder()
        .apply {
            this += LoggingInterceptor()
        }
        .withViewModel(
            inputHandler = PostPageInputHandler(ApiClient(Js.create())),
            initialState = initialState,
            name = "PostPage",
        )
        .build(),
    eventHandler = PostPageEventHandler(),
)
