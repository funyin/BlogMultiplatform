package org.example.blogmultiplatform.data.repository

import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.models.ApiResponse
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.res.CommonRes

class SubscribersRepository(val client: ApiClient) {
    suspend fun addSubscriber(email: String): UiState<String> {
        return try {
            val response = client.get<ApiResponse<Boolean>>(
                "${CommonRes.Strings.apiBaseUrl}subscribers/add",
                parameters = mutableMapOf("email" to email)
            )
            val success = response.data
            UiState.Success(if (success) "Success" else "Already Exists")
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message.toString())
        }
    }
}