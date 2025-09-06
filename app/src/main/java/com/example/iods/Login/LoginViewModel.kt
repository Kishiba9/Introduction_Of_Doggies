package com.example.iods.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateLoginId(newId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                loginId = newId,
                loginIdError = false // 入力変更時にエラー状態をリセット
            )
        }
    }

    fun updatePassword(newPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = newPassword,
                passwordError = false // 入力変更時にエラー状態をリセット
            )
        }
    }

    fun login() {
        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                isLoggedIn = false
            )
        } // ログイン処理開始時にisLoggedInをfalseにリセット

        if (uiState.value.loginId.isEmpty()) {
            _uiState.update {
                it.copy(
                    loginIdError = true,
                    isLoading = false,
                    errorMessage = "ログインIDを入力してください"
                )
            }
            return
        }

        if (uiState.value.password.isEmpty()) {
            _uiState.update {
                it.copy(
                    passwordError = true,
                    isLoading = false,
                    errorMessage = "パスワードを入力してください"
                )
            }
            return
        }

        viewModelScope.launch { // ViewModelのライフサイクルに紐づくコルーチンスコープ
            try {
                val success = performLogin(uiState.value.loginId, uiState.value.password)

                if(success) {
                    _uiState.update {it.copy(isLoading = false, errorMessage = null, isLoggedIn = true)}
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "ログイン失敗：IDまたはパスワードが間違っています", isLoggedIn = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "エラーが発生しました", isLoggedIn = false) }
            }
        }
    }

    private suspend fun performLogin(loginId: String, password: String): Boolean {
        delay(2000) //ネットワークシミュレーション
        return loginId == "dog" && password == "0"
    }

}