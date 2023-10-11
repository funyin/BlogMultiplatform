package org.example.blogmultiplatform.modules.posts.create

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.varabyte.kobweb.core.PageContext
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.postSuccess
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.Events
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.Inputs
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.State

class CreatePostScreenEventHandler(val pageContext: PageContext) :
    EventHandler<Inputs, Events, State> {

    override suspend fun EventHandlerScope<Inputs, Events, State>.handleEvent(event: Events) =
        when (event) {
            Events.PostCreated -> {
                pageContext.router.navigateTo(Res.Routes.postSuccess(message = "Post Successfully Created!"))
            }

            Events.PostUpdated -> {
                pageContext.router.navigateTo(Res.Routes.postSuccess(message = "Post Successfully Updated!"))
            }
        }
}