package com.example.simplelibrarymanagement.presentation.ui.screen.auth.login

data class LoginUiState(
    val email: String = "", // Changed from username
    val password: String = "",
    val emailError: String? = null, // Changed from usernameError
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = email.isNotBlank() &&
                password.isNotBlank() &&
                emailError == null &&
                passwordError == null
}