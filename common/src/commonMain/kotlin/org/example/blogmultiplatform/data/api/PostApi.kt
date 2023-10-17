package org.example.blogmultiplatform.data.api

import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.models.CreatePostRequest
import org.example.blogmultiplatform.models.PostLight

interface PostApi {
    suspend fun createPost(post: CreatePostRequest): Boolean
    suspend fun posts(page: Int, size: Int): List<PostLight>

    val apiClient: ApiClient
}