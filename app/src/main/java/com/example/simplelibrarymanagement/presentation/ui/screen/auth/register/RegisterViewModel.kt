package com.example.simplelibrarymanagement.presentation.ui.screen.auth.register

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
class RegisterViewModel @Inject constructor(
    // Inject your authentication repository here if you have one
    // private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(
            username = username,
            usernameError = validateUsername(username)
        )
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = validateEmail(email)
        )
    }

    fun updatePassword(password: String) {
        val confirmPasswordError = if (_uiState.value.confirmPassword.isNotEmpty()) {
            validateConfirmPassword(password, _uiState.value.confirmPassword)
        } else {
            null
        }
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = validatePassword(password),
            confirmPasswordError = confirmPasswordError
        )
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = confirmPassword,
            confirmPasswordError = validateConfirmPassword(_uiState.value.password, confirmPassword)
        )
    }

    fun register() {
        val currentState = _uiState.value

        val usernameError = validateUsername(currentState.username)
        val emailError = validateEmail(currentState.email)
        val passwordError = validatePassword(currentState.password)
        val confirmPasswordError = validateConfirmPassword(currentState.password, currentState.confirmPassword)

        if (usernameError != null || emailError != null || passwordError != null || confirmPasswordError != null) {
            _uiState.value = currentState.copy(
                usernameError = usernameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, errorMessage = null)
            try {
                // Simulate API call
                delay(2000)

                // Mock registration logic - replace with your actual backend call
                if (currentState.username == "taken") { // Contoh validasi sederhana
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = "Username already taken."
                    )
                } else {
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        isRegistrationSuccess = true
                    )
                }
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Registration failed: ${e.message}"
                )
            }
        }
    }

    private fun validateUsername(username: String): String? {
        return when {
            username.isBlank() -> "Username is required"
            username.length < 3 -> "Username must be at least 3 characters"
            else -> null
        }
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isBlank() -> "Confirm password is required"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetRegistrationSuccess() {
        _uiState.value = _uiState.value.copy(isRegistrationSuccess = false)
    }
}