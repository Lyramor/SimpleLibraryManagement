package com.example.simplelibrarymanagement.presentation.ui.screen.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val generalError: String? = null,
    val isLoginSuccessful: Boolean = false,
    val rememberMe: Boolean = false
) {
    val isFormValid: Boolean
        get() = email.isNotBlank() &&
                password.isNotBlank() &&
                emailError == null &&
                passwordError == null
}