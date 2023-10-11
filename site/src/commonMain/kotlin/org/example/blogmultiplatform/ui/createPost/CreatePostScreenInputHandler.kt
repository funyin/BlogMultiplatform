package org.example.blogmultiplatform.ui.createPost

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.postInput
import kotlinx.datetime.Clock
import org.example.blogmultiplatform.data.api.PostApi
import org.example.blogmultiplatform.data.repository.PostRepository
import org.example.blogmultiplatform.models.*
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.Events
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.Inputs
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.State

class CreatePostScreenInputHandler(private val postApi: PostApi) : InputHandler<Inputs, Events, State> {
    private val repository = PostRepository(postApi.apiClient)
    override suspend fun InputHandlerScope<Inputs, Events, State>.handleInput(
        input: Inputs
    ) {
        when (input) {
            is Inputs.UpdateTitle -> {
                updateState {
                    it.copy(title = input.value)
                }
            }

            is Inputs.ToggleEditorKey -> {
                val uiState = getCurrentState()
                val list = uiState.activeKeys.toMutableList()
                val key = input.key
                if (list.contains(key)) list.remove(key)
                else {
                    list.add(key)
                }
                when (list.contains(EditorKey.Link)) {
                    true -> {
                        postInput(
                            Inputs.ShowLinkPopup(range = input.selection, key)
                        )
                    }

                    false -> {
                    }
                }
                if (list.size > uiState.activeKeys.size) {
                    if (key == EditorKey.Link || key == EditorKey.Image) {
                        postInput(Inputs.ShowLinkPopup(range = input.selection, key))
                    } else {
                        val content = uiState.content.replaceRange(
                            input.selection, list.last().controlStyle(uiState.content.slice(input.selection))
                        )
                        updateState { it.copy(activeKeys = list, content = content) }
                    }
                } else {
                    updateState { it.copy(activeKeys = list) }
                }
            }

            is Inputs.ImageUrl -> {
                updateState {
                    it.copy(imageUrl = input.value)
                }
            }

            Inputs.ToggleShowPreview -> {
                updateState {
                    it.copy(showPreview = !it.showPreview)
                }
            }

            is Inputs.UpdateCategory -> {
                updateState {
                    it.copy(category = input.value)
                }
            }

            is Inputs.UpdateContent -> {
                updateState {
                    it.copy(content = input.value)
                }
            }

            is Inputs.UpdateMain -> {
                updateState {
                    it.copy(main = input.value)
                }
            }

            is Inputs.UpdatePastImageUrl -> {
                updateState {
                    it.copy(pasteImageUrl = input.value)
                }
            }

            is Inputs.UpdatePopular -> {
                updateState {
                    it.copy(popular = input.value)
                }
            }

            is Inputs.UpdateSponsored -> {
                updateState {
                    it.copy(sponsored = input.value)
                }
            }

            is Inputs.UpdateSubtitleTitle -> {
                updateState {
                    it.copy(subtitle = input.value)
                }
            }

            is Inputs.CreatePost -> {
                val uiState = getCurrentState()
                if (!sanityCheck(uiState))
                    return
                val request = buildRequest(uiState)
                updateState {
                    it.copy(createPostState = UiState.Loading)
                }
                sideJob("createPost") {
                    try {
                        val response = postApi.createPost(request)
                        postInput(Inputs.CreatePostResponse(UiState.Success(response)))
                        if (response) postEvent(Events.PostCreated)
                    } catch (e: Exception) {
                        postInput(Inputs.CreatePostResponse(UiState.Error(e.message.toString())))
                    }
                }
            }

            is Inputs.CreatePostResponse -> {
                updateState {
                    if (input.state.isSuccess) State.initial.copy(createPostState = input.state)
                    else it.copy(createPostState = input.state)
                }
            }

            is Inputs.ClosePopup -> {
                updateState {
                    it.copy(createPostState = UiState.Initial, updatePostState = UiState.Initial)
                }
            }

            is Inputs.ShowErrorMessage -> {
                val errorStat = UiState.Error<Boolean>(errorMessage = input.message)
                updateState {
                    it.copy(createPostState = errorStat, updatePostState = errorStat)
                }
            }

            is Inputs.ShowLinkPopup -> {
                updateState {
                    if (input.keyType == EditorKey.Link) it.copy(showLinkPopup = input.range) else it.copy(
                        showImagePopup = input.range
                    )
                }
            }

            is Inputs.CloseLinkPopup -> {
                updateState { it.copy(showLinkPopup = null) }
            }

            Inputs.CloseImagePopup -> {
                updateState { it.copy(showImagePopup = null) }
            }

            Inputs.GetPost -> {
                updateState { it.copy(getPostState = UiState.Loading) }
                val postId = getCurrentState().postId!!
                sideJob("getPost") {
                    try {
                        val response = repository.post(postId)
                        postInput(Inputs.GetPostResponse(response))
                    } catch (e: Exception) {
                        postInput(Inputs.GetPostResponse(UiState.Error(e.message.toString())))
                    }
                }
            }

            is Inputs.GetPostResponse -> {
                val postState = input.state
                updateState { it.copy(getPostState = postState) }
                if (postState is UiState.Success) {
                    val post = postState.data
                    updateState {
                        it.copy(
                            title = post.title,
                            subtitle = post.subtitle,
                            content = post.content,
                            category = Category.valueOf(post.category),
                            postId = post.id,
                            popular = post.popular,
                            sponsored = post.sponsored,
                            imageUrl = post.thumbnail,
                            main = post.main,
                        )
                    }
                }
            }

            Inputs.UpdatePost -> {
                val uiState = getCurrentState()
                if (!sanityCheck(uiState))
                    return
                val request = buildRequest(uiState).run {
                    UpdatePostRequest.from(this, postId = uiState.postId!!)
                }
                updateState { it.copy(updatePostState = UiState.Loading) }
                sideJob("updatePost") {
                    try {
                        val response = repository.updatePost(request)
                        postInput(Inputs.UpdatePostResponse(response))
                    } catch (e: Exception) {
                        postInput(Inputs.UpdatePostResponse(UiState.Error(e.message.toString())))
                    }
                }
            }

            is Inputs.UpdatePostResponse -> {
                updateState { it.copy(updatePostState = input.state) }
                if (input.state.isSuccess)
                    postEvent(Events.PostUpdated)
            }
        }
    }

    private fun buildRequest(uiState: State) =
        CreatePostRequest(
            date = Clock.System.now().toEpochMilliseconds(),
            title = uiState.title,
            subtitle = uiState.subtitle,
            content = uiState.content,
            thumbnail = uiState.imageUrl,
            popular = uiState.popular,
            category = uiState.category?.name.toString(),
            main = uiState.main,
            sponsored = uiState.sponsored
        )

    private suspend fun InputHandlerScope<Inputs, Events, State>.sanityCheck(
        uiState: State
    ): Boolean {
        with(uiState) {
            if (title.isEmpty() || subtitle.isEmpty() || content.isEmpty() || imageUrl.isEmpty() || category == null) {
                postInput(Inputs.ShowErrorMessage(message = "Fill all required fields"))
                return false
            }
            return true
        }
    }
}