package org.example.blogmultiplatform.data

import org.example.blogmultiplatform.models.LoginRequest
import org.example.blogmultiplatform.models.User

interface MongoRepository {
    suspend fun login(request: LoginRequest): User?
    suspend fun checkUserId(userId: String): Boolean
}