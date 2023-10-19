package org.example.blogmultiplatform.modules.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.blogmultiplatform.models.LoginRequest
import org.example.blogmultiplatform.models.User

class AuthViewModel(private val scope: CoroutineScope) {
    private val repository = AuthRepository()
    val loginState = MutableStateFlow<Result<User>?>(null)

    fun clearLoginState() {
        loginState.value = null
    }

    fun login(userName: String, password: String) {

        try {
            scope.launch {
                val response = repository.login(
                    LoginRequest(
                        userName = userName,
                        password = password
                    )
                )
                if (response == null) {
                    loginState.value = Result.failure(Throwable(message = "User does not exist"))
                    return@launch
                }
                loginState.value = Result.success(response)
            }
        } catch (e: Throwable) {
            loginState.update {
                Result.failure(e)
            }
        }
    }
}