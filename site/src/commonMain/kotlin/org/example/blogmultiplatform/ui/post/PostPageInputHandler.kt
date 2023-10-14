package org.example.blogmultiplatform.ui.post

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.data.repository.PostRepository
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.ui.post.PostPageContract.Inputs.GetData

class PostPageInputHandler(client: ApiClient) : InputHandler<
        PostPageContract.Inputs,
        PostPageContract.Events,
        PostPageContract.State> {

    val postRepository = PostRepository(client)
    override suspend fun InputHandlerScope<
            PostPageContract.Inputs,
            PostPageContract.Events,
            PostPageContract.State>.handleInput(
        input: PostPageContract.Inputs
    ): Unit = when (input) {
        is GetData -> {
            sideJob("getData") {
                postInput(GetData.State(UiState.Loading))
                postInput(GetData.State(postRepository.post(input.postId)))
            }
        }

        is GetData.State -> {
            updateState { it.copy(postsState = input.state) }
        }
    }
}
