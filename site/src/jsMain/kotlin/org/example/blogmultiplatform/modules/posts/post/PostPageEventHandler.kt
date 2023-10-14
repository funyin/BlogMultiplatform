package org.example.blogmultiplatform.modules.posts.post

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import org.example.blogmultiplatform.ui.post.PostPageContract

class PostPageEventHandler : EventHandler<
        PostPageContract.Inputs,
        PostPageContract.Events,
        PostPageContract.State> {
    override suspend fun EventHandlerScope<
            PostPageContract.Inputs,
            PostPageContract.Events,
            PostPageContract.State>.handleEvent(
        event: PostPageContract.Events
    ) {

    }
}
