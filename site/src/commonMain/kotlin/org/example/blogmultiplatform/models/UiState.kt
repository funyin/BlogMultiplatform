package org.example.blogmultiplatform.models

import kotlinx.serialization.Serializable

@Serializable
sealed class UiState<T> {
    @Serializable
    data class Success<T>(val data: T) : UiState<T>()

    @Serializable
    class Loading<T> : UiState<T>()

    @Serializable
    data class Error<T>(val errorMessage: String) : UiState<T>()

    @Serializable
    class Initial<T> : UiState<T>()

    val isLoading: Boolean
        get() = this is Loading
    val isError: Boolean
        get() = this is Error
    val isSuccess: Boolean
        get() = this is Success

    fun <W> whenOn(error: () -> W, loading: () -> W, success: () -> W, default: () -> W): W = when (this) {
        is Error -> error()
        is Loading -> loading()
        is Success -> success()
        else -> default()
    }
}