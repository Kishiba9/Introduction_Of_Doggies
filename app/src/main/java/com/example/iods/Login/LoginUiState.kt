package com.example.iods.Login

data class LoginUiState(
    val loginId: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginIdError: Boolean = false,
    val passwordError: Boolean = false,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false
)
