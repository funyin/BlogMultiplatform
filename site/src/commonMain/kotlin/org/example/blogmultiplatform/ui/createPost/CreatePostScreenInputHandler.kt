package org.example.blogmultiplatform.ui.createPost

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.postInput
import kotlinx.datetime.Clock
import org.example.blogmultiplatform.api.PostApi
import org.example.blogmultiplatform.models.CreatePosRequest
import org.example.blogmultiplatform.models.EditorKey
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.models.controlStyle
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.Events
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.Inputs
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.State

class CreatePostScreenInputHandler(private val postApi: PostApi) : InputHandler<Inputs, Events, State> {
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
                with(uiState) {
                    if (title.isEmpty() || subtitle.isEmpty() || content.isEmpty() || imageUrl.isEmpty() || category == null) {
                        postInput(Inputs.ShowErrorMessage(message = "Fill all required fields"))
                        return@handleInput
                    }
                }
                val request = CreatePosRequest(
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
                    if (input.createPostState.isSuccess) State.initial.copy(createPostState = input.createPostState)
                    else it.copy(createPostState = input.createPostState)
                }
            }

            is Inputs.ClosePopup -> {
                updateState {
                    it.copy(createPostState = UiState.Initial)
                }
            }

            is Inputs.ShowErrorMessage -> {
                updateState {
                    it.copy(createPostState = UiState.Error(errorMessage = input.message))
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
        }
    }
}