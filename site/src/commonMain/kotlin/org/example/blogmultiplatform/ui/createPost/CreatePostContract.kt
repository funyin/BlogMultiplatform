package org.example.blogmultiplatform.ui.createPost

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.models.EditorKey
import org.example.blogmultiplatform.models.UiState

object CreatePostContract {

    @Serializable
    data class State(
        val popular: Boolean = false,
        val main: Boolean = false,
        val sponsored: Boolean = false,
        val title: String = "",
        val subtitle: String = "",
        val category: Category? = null,
        val pasteImageUrl: Boolean = false,
        val imageUrl: String = "",
        val activeKeys: List<EditorKey> = emptyList(),
        val showPreview: Boolean = false,
        val content: String = "",
        @Transient
        val createPostState: UiState<Boolean> = UiState.Initial,
        @Transient
        val showLinkPopup: IntRange? = null,
        @Transient
        val showImagePopup: IntRange? = null
    ) {
        companion object {
            val initial = State()
        }
    }

    sealed interface Inputs {
        data class UpdatePopular(val value: Boolean) : Inputs
        data class UpdateMain(val value: Boolean) : Inputs
        data class UpdateSponsored(val value: Boolean) : Inputs
        data class UpdateTitle(val value: String) : Inputs
        data class UpdateSubtitleTitle(val value: String) : Inputs
        data class UpdateCategory(val value: Category) : Inputs
        data class UpdatePastImageUrl(val value: Boolean) : Inputs
        data class ImageUrl(val value: String) : Inputs
        data class ToggleEditorKey(val key: EditorKey, val selection: IntRange) : Inputs
        data object ToggleShowPreview : Inputs
        data class UpdateContent(val value: String) : Inputs
        data object CreatePost : Inputs
        data class CreatePostResponse(val createPostState: UiState<Boolean>) : Inputs
        data object ClosePopup : Inputs
        data class ShowErrorMessage(val message: String) : Inputs
        data class ShowLinkPopup(val range: IntRange, val keyType: EditorKey) : Inputs
        data object CloseLinkPopup : Inputs
        data object CloseImagePopup : Inputs
    }

    sealed interface Events {
        data object PostCreated : Events
    }
}