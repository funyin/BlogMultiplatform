package org.example.blogmultiplatform.api

import org.example.blogmultiplatform.models.CreatePosRequest

interface PostApi {
    suspend fun createPost(post: CreatePosRequest): Boolean
}