package com.example.simplelibrarymanagement.presentation.ui.screen.auth.login

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
class LoginViewModel @Inject constructor(
    // Inject your authentication repository here
    // private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(
            username = username,
            usernameError = validateUsername(username)
        )
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = validatePassword(password)
        )
    }

    fun login() {
        val currentState = _uiState.value

        // Validate form before submitting
        val usernameError = validateUsername(currentState.username)
        val passwordError = validatePassword(currentState.password)

        if (usernameError != null || passwordError != null) {
            _uiState.value = currentState.copy(
                usernameError = usernameError,
                passwordError = passwordError
            )
            return
        }

        // Proceed with login
        viewModelScope.launch {
            _uiState.value = currentState.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                // Simulate API call - replace with actual authentication logic
                delay(2000) // Simulate network delay

                // Mock authentication - replace with real implementation
                val isLoginSuccessful = authenticateUser(
                    currentState.username,
                    currentState.password
                )

                if (isLoginSuccessful) {
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        isLoginSuccess = true
                    )
                } else {
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = "Invalid username or password"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Login failed. Please try again."
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

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }

    private suspend fun authenticateUser(username: String, password: String): Boolean {
        // Mock authentication logic
        // Replace this with actual API call to your backend
        return username == "admin" && password == "password123"
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetLoginSuccess() {
        _uiState.value = _uiState.value.copy(isLoginSuccess = false)
    }
}