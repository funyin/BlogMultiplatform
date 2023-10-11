package org.example.blogmultiplatform.api

import org.example.blogmultiplatform.models.CreatePosRequest
import org.example.blogmultiplatform.models.PostLight

interface PostApi {
    suspend fun createPost(post: CreatePosRequest): Boolean
    suspend fun posts(page: Int, size: Int): List<PostLight>
}