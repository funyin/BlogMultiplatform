package org.example.blogmultiplatform.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.blogmultiplatform.data.api.core.ApiClient
import org.example.blogmultiplatform.models.*
import org.example.blogmultiplatform.res.CommonRes

class PostRepository(private val client: ApiClient) {
    suspend fun posts(search: String? = null, page: Int, size: Int): UiState<List<PostLight>> {
        return try {
            val response = client.get<ApiResponse<List<PostLight>>>("${CommonRes.Strings.apiBaseUrl}posts",
                parameters = mutableMapOf("page" to page, "size" to size).run {
                    if (!search.isNullOrEmpty()) this + ("search" to search)
                    else this
                })
            UiState.Success(response.data)
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message!!.toString())
        }
    }

    suspend fun post(postId: String): UiState<Post> {
        return try {
            val response = client.get<ApiResponse<Post>>(
                "${CommonRes.Strings.apiBaseUrl}post",
                parameters = mutableMapOf("postId" to postId)
            )
            UiState.Success(response.data)
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message!!.toString())
        }
    }

    suspend fun deletePosts(items: List<String>): Flow<UiState<Boolean>> = flow {
        try {
            emit(UiState.Loading)
            val response = client.post<ApiResponse<Boolean>>(
                "${CommonRes.Strings.apiBaseUrl}posts/deletemany", body = DeletePostsRequest(items)
            )
            emit(UiState.Success(response.data))
        } catch (e: Exception) {
            emit(UiState.Error(errorMessage = e.message!!.toString()))
        }
    }

    suspend fun updatePost(post: UpdatePostRequest): UiState<Boolean> {
        return try {
            val response =
                client.put<ApiResponse<Boolean>>("${CommonRes.Strings.apiBaseUrl}posts/update", body = post)
            UiState.Success(response.data)
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message!!.toString())
        }
    }

    suspend fun mainPosts(): UiState<List<PostLight>> {
        return try {
            val response =
                client.get<ApiResponse<List<PostLight>>>("${CommonRes.Strings.apiBaseUrl}posts/main")
            UiState.Success(response.data)
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message!!.toString())
        }
    }

    suspend fun latestPosts(page: Int, size: Int): UiState<List<PostLight>> {
        return try {
            val response =
                client.get<ApiResponse<List<PostLight>>>(
                    "${CommonRes.Strings.apiBaseUrl}posts/latest",
                    parameters = mapOf("page" to page, "size" to size)
                )
            UiState.Success(response.data)
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message!!.toString())
        }
    }

    suspend fun sponsoredPosts(): UiState<List<PostLight>> {
        return try {
            val response =
                client.get<ApiResponse<List<PostLight>>>("${CommonRes.Strings.apiBaseUrl}posts/sponsored")
            UiState.Success(response.data)
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message!!.toString())
        }
    }

    suspend fun popularPosts(page: Int, size: Int): UiState<List<PostLight>> {
        return try {
            val response =
                client.get<ApiResponse<List<PostLight>>>(
                    "${CommonRes.Strings.apiBaseUrl}posts/popular",
                    parameters = mapOf("page" to page, "size" to size)
                )
            UiState.Success(response.data)
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message!!.toString())
        }
    }

    suspend fun search(title: String?, category: Category?, page: Int, size: Int): UiState<List<PostLight>> {
        return try {
            val response =
                client.get<ApiResponse<List<PostLight>>>(
                    "${CommonRes.Strings.apiBaseUrl}posts/search",
                    parameters = mutableMapOf<String, Any>(
                        "page" to page,
                        "size" to size,
                    ).run {
                        category?.name?.let {
                            this += "category" to it
                        }
                        title?.let {
                            this += "title" to title
                        }
                        this
                    }
                )
            UiState.Success(response.data)
        } catch (e: Exception) {
            UiState.Error(errorMessage = e.message!!.toString())
        }
    }
}