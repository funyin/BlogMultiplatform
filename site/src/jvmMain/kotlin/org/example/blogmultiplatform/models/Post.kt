package org.example.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@Serializable
actual data class Post(
    @SerialName("_id")
    @BsonProperty("_id")
    @BsonId
    actual val id: String = ObjectId().toHexString(),
    actual val date: Long,
    actual val title: String,
    actual val subtitle: String,
    actual val thumbnail: String,
    actual val content: String,
    actual val category: String,
    actual val popular: Boolean,
    actual val main: Boolean,
    actual val sponsored: Boolean,
)

@Serializable
actual data class PostLight(
    @SerialName("_id")
    @BsonProperty("_id")
    actual val id: String = ObjectId().toHexString(),
    actual val date: Long,
    actual val title: String,
    actual val subtitle: String,
    actual val thumbnail: String,
    actual val category: String,
)

@Serializable
actual data class UpdatePostRequest(
    @SerialName("_id")
    @BsonProperty("_id")
    @BsonId
    actual val id: String,
    actual val date: Long,
    actual val title: String,
    actual val subtitle: String,
    actual val thumbnail: String,
    actual val content: String,
    actual val category: String,
    actual val popular: Boolean,
    actual val main: Boolean,
    actual val sponsored: Boolean,
){
    actual companion object {
        actual fun from(request: CreatePostRequest, postId: String): UpdatePostRequest {
            return request.run {
                UpdatePostRequest(
                    id = postId,
                    date = date,
                    title = title,
                    subtitle = subtitle,
                    thumbnail = thumbnail,
                    content = content,
                    category = category,
                    popular = popular,
                    main = main,
                    sponsored = sponsored,
                )
            }
        }

    }
}
