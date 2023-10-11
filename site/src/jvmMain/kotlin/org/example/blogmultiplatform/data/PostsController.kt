package org.example.blogmultiplatform.data

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts.descending
import kotlinx.coroutines.flow.toList
import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.PostLight

object PostsController {
    suspend fun ApiController.addPost(post: Post): Boolean {
        return postsCollection.insertOne(post).wasAcknowledged()
    }

    suspend fun ApiController.getPosts(
        search: String?,
        page: Int,
        size: Int
    ): List<PostLight> {
        val searchPattern = search?.toRegex(RegexOption.IGNORE_CASE)?.toPattern()
        return postsCollection
            .withDocumentClass(PostLight::class.java)
            .find(
                searchPattern
                    ?.let { Filters.regex(PostLight::title.name, it) } ?: Filters.empty()
            )
            .sort(sort = descending(PostLight::date.name))
            .skip((page - 1) * size).limit(size).toList()
    }

    suspend fun ApiController.deletePosts(items: List<String>): Boolean {
        return postsCollection
            .deleteMany(Filters.`in`("_id", items))
            .wasAcknowledged()
    }
}