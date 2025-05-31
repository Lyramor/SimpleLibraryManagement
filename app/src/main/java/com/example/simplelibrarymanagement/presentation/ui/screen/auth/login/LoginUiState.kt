package com.example.simplelibrarymanagement.presentation.ui.screen.auth.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val usernameError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = username.isNotBlank() &&
                password.isNotBlank() &&
                usernameError == null &&
                passwordError == null
}