package com.example.simplelibrarymanagement.presentation.ui.screen.auth.forgotpassword

data class ForgotPasswordUiState(
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val isResetSuccess: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
) {
    val isFormValid: Boolean
        get() = email.isNotBlank() && emailError == null
}