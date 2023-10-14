package org.example.blogmultiplatform.data

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts.descending
import com.mongodb.client.model.Updates
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.example.blogmultiplatform.models.Post
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UpdatePostRequest

object PostsController {
    suspend fun ApiController.addPost(post: Post): Boolean {
        return postsCollection.insertOne(post).wasAcknowledged()
    }

    suspend fun ApiController.updatePost(post: UpdatePostRequest): Boolean {
        return postsCollection
            .withDocumentClass<UpdatePostRequest>()
            .updateOne(
                Filters.eq("_id", post.id), listOf(
                    Updates.set(UpdatePostRequest::date.name, post.date),
                    Updates.set(UpdatePostRequest::title.name, post.title),
                    Updates.set(UpdatePostRequest::subtitle.name, post.subtitle),
                    Updates.set(UpdatePostRequest::thumbnail.name, post.thumbnail),
                    Updates.set(UpdatePostRequest::content.name, post.content),
                    Updates.set(UpdatePostRequest::category.name, post.category),
                    Updates.set(UpdatePostRequest::popular.name, post.popular),
                    Updates.set(UpdatePostRequest::main.name, post.main),
                    Updates.set(UpdatePostRequest::sponsored.name, post.sponsored)
                )
            ).wasAcknowledged()
    }

    suspend fun ApiController.get(postId: String): Post? {
        return postsCollection.find(Filters.eq("_id", postId)).firstOrNull()
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

    suspend fun ApiController.mainPosts(): List<PostLight> {
        return postsCollection
            .withDocumentClass<PostLight>()
            .find(Filters.eq(Post::main.name, true))
            .sort(descending(PostLight::date.name))
            .limit(4)
            .toList()
    }

    suspend fun ApiController.sponsoredPosts(): List<PostLight> {
        return postsCollection
            .withDocumentClass<PostLight>()
            .find(Filters.eq(Post::sponsored.name, true))
            .sort(descending(PostLight::date.name))
            .limit(2)
            .toList()
    }

    suspend fun ApiController.latest(page: Int, size: Int): List<PostLight> {
        return postsCollection
            .withDocumentClass<PostLight>()
            .find(
                Filters.and(
                    listOf(
                        Filters.eq(Post::main.name, false),
                        Filters.eq(Post::popular.name, false),
                        Filters.eq(Post::sponsored.name, false),
                    )
                )
            )
            .sort(descending(PostLight::date.name))
            .sort(sort = descending(PostLight::date.name))
            .skip((page - 1) * size).limit(size)
            .toList()
    }

    suspend fun ApiController.popular(page: Int, size: Int): List<PostLight> {
        return postsCollection
            .withDocumentClass<PostLight>()
            .find(Filters.eq(Post::popular.name, true))
            .sort(descending(PostLight::date.name))
            .sort(sort = descending(PostLight::date.name))
            .skip((page - 1) * size).limit(size)
            .toList()
    }
}