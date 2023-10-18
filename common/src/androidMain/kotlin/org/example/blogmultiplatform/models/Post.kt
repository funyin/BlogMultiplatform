package org.example.blogmultiplatform.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PersistedName
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class Post(
    @SerialName("_id")
    actual val id: String,
    actual val date: Long,
    actual val title: String,
    actual val subtitle: String,
    actual val thumbnail: String,
    actual val content: String,
    actual val category: String,
    actual val popular: Boolean,
    actual val main: Boolean,
    actual val sponsored: Boolean
)

@Serializable
actual class PostLight() : RealmObject{
    @PrimaryKey
    @PersistedName("_id")
    @SerialName("_id")
    actual var id: String = ""
    actual var date: Long = 0
    actual var title: String = ""
    actual var subtitle: String = ""
    actual var thumbnail: String = ""
    actual var category: String = ""
}

@Serializable
actual data class UpdatePostRequest(
    @SerialName("_id")
    actual val id: String,
    actual val date: Long,
    actual val title: String,
    actual val subtitle: String,
    actual var thumbnail: String,
    actual val content: String,
    actual val category: String,
    actual val popular: Boolean,
    actual val main: Boolean,
    actual val sponsored: Boolean,
) {
    actual companion object {
        actual fun from(request: CreatePostRequest, postId: String): UpdatePostRequest {
            return request.run {
                UpdatePostRequest(
                    postId,
                    date,
                    title,
                    subtitle,
                    thumbnail,
                    content,
                    category,
                    popular,
                    main,
                    sponsored,
                )
            }
        }

    }
}