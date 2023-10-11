package org.example.blogmultiplatform.models

sealed class UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>()
    data object Loading : UiState<Nothing>()
    data class Error<T>(val errorMessage: String) : UiState<T>()
    data object Initial : UiState<Nothing>()

    val isInitial: Boolean
        get() = this is Initial
    val isLoading: Boolean
        get() = this is Loading
    val isError: Boolean
        get() = this is Error
    val isSuccess: Boolean
        get() = this is Success
    val getData: T
        get() = (this as Success).data

    fun <W> whenOn(error: ((String) -> W)? = null, loading: W? = null, success: ((T) -> W)? = null, default: W) =
        when (this) {
            is Error -> error?.invoke(errorMessage)
            is Loading -> loading
            is Success -> success?.invoke(getData)
            else -> default
        }
}