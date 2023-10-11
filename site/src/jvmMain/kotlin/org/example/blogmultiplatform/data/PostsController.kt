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
        page: Int,
        size: Int
    ): List<PostLight> {
        return postsCollection
            .withDocumentClass(PostLight::class.java)
            .find()
            .sort(sort = descending(PostLight::date.name))
            .skip((page - 1) * size).limit(size).toList()
    }

    suspend fun ApiController.deletePosts(items: List<String>): Boolean {
        return postsCollection
            .deleteMany(Filters.`in`("_id", items))
            .wasAcknowledged()
    }
}