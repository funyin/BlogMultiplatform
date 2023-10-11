package org.example.blogmultiplatform.modules.posts

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.blogmultiplatform.core.network.ApiClient
import org.example.blogmultiplatform.models.ApiResponse
import org.example.blogmultiplatform.models.DeletePostsRequest
import org.example.blogmultiplatform.models.PostLight
import org.example.blogmultiplatform.models.UiState
import org.example.blogmultiplatform.res.Res
import org.example.blogmultiplatform.res.apiBaseUrl

class PostRepository {
    suspend fun posts(page: Int, size: Int): UiState<List<PostLight>> {
        return try {
            val response = ApiClient.get<ApiResponse<List<PostLight>>>(
                "${Res.Strings.apiBaseUrl}posts",
                parameters = mapOf("page" to page, "size" to size)
            )
            UiState.Success(response.data)
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message!!.toString())
        }

    }

    suspend fun deletePosts(items: List<String>): Flow<UiState<Boolean>> = flow {
        try {
            emit(UiState.Loading)
            val response = ApiClient.post<ApiResponse<Boolean>>(
                "${Res.Strings.apiBaseUrl}posts/deletemany",
                body = DeletePostsRequest(items)
            )
            emit(UiState.Success(response.data))
        } catch (e: Exception) {
            emit(UiState.Error(errorMessage = e.message!!.toString()))
        }
    }
}