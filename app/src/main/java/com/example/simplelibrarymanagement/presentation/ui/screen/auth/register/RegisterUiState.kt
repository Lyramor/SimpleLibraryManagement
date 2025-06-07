package com.example.simplelibrarymanagement.presentation.ui.screen.auth.register

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val isRegistrationSuccess: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = username.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                confirmPassword.isNotBlank() &&
                usernameError == null &&
                emailError == null &&
                passwordError == null &&
                confirmPasswordError == null &&
                password == confirmPassword
}