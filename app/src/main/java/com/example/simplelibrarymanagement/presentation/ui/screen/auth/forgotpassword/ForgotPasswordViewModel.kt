package com.example.simplelibrarymanagement.presentation.ui.screen.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = null
        )
        validateEmail(email)
    }

    private fun validateEmail(email: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

        val error = when {
            email.isBlank() -> "Email cannot be empty"
            !email.matches(emailRegex) -> "Please enter a valid email address"
            else -> null
        }

        _uiState.value = _uiState.value.copy(emailError = error)
    }

    fun resetPassword() {
        if (!_uiState.value.isFormValid) {
            validateEmail(_uiState.value.email)
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                // Simulate API call
                delay(2000)

                // Simulate success (you would replace this with actual API call)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isResetSuccess = true,
                    successMessage = "Password reset link has been sent to your email"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to send reset link. Please try again."
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun clearSuccess() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }

    fun resetState() {
        _uiState.value = _uiState.value.copy(
            isResetSuccess = false,
            successMessage = null
        )
    }
}