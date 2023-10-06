package org.example.blogmultiplatform.ui.createPost

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.Events
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.Inputs
import org.example.blogmultiplatform.ui.createPost.CreatePostContract.State

class CreatePostScreenInputHandler : InputHandler<Inputs, Events, State> {
    override suspend fun InputHandlerScope<Inputs, Events, State>.handleInput(
        input: Inputs
    ) = when (input) {
        is Inputs.UpdateTitle -> {
            updateState {
                it.copy(title = input.value)
            }
        }

        is Inputs.ToggleEditorKey -> {
            updateState {
                val list = it.activeKeys.toMutableList()
                val key = input.key
                if (list.contains(key))
                    list.remove(key)
                else
                    list.add(key)
                it.copy(activeKeys = list)
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
    }
}