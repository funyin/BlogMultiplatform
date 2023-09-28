package org.example.blogmultiplatform.modules.auth

import org.example.blogmultiplatform.models.LoginRequest
import org.example.blogmultiplatform.models.User

class AuthRepository {
    private val api = AuthApi
    suspend fun login(request: LoginRequest): User? = api.login(request)
    suspend fun checkUserId(userId: String): Boolean = api.checkUserId(userId)
}