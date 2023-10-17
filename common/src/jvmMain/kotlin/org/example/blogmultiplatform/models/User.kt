package org.example.blogmultiplatform.models

import kotlinx.serialization.SerialName
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

actual data class User(
    @SerialName(value = "_id")
    @BsonId
    actual val id: String = ObjectId().toHexString(),
    actual val userName: String = "",
    @Transient
    val password: String = ""
)