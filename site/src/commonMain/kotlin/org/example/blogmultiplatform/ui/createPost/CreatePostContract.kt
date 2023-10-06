package org.example.blogmultiplatform.ui.createPost

import kotlinx.serialization.Serializable
import org.example.blogmultiplatform.models.Category
import org.example.blogmultiplatform.models.EditorKey

object CreatePostContract {

    @Serializable
    data class State(
        val popular: Boolean = false,
        val main: Boolean = false,
        val sponsored: Boolean = false,
        val title: String? = null,
        val subtitle: String? = null,
        val category: Category? = null,
        val pasteImageUrl: Boolean = false,
        val imageUrl: String? = null,
        val activeKeys: List<EditorKey> = emptyList(),
        val showPreview: Boolean = false,
        val content: String? = null,
    )

    sealed interface Inputs {
        data class UpdatePopular(val value: Boolean) : Inputs
        data class UpdateMain(val value: Boolean) : Inputs
        data class UpdateSponsored(val value: Boolean) : Inputs
        data class UpdateTitle(val value: String) : Inputs
        data class UpdateSubtitleTitle(val value: String) : Inputs
        data class UpdateCategory(val value: Category) : Inputs
        data class UpdatePastImageUrl(val value: Boolean) : Inputs
        data class ImageUrl(val value: String) : Inputs
        data class ToggleEditorKey(val key: EditorKey) : Inputs
        data object ToggleShowPreview : Inputs
        data class UpdateContent(val value: String) : Inputs
    }

    sealed interface Events {
        data object CreatePost : Events
    }
}