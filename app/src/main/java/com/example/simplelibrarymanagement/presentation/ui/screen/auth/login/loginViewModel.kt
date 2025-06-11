package com.example.simplelibrarymanagement.presentation.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.data.model.LoginRequest
import com.example.simplelibrarymanagement.data.remote.AuthTokenManager
import com.example.simplelibrarymanagement.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    // --- THIS IS THE FIX ---
    private val tokenManager: AuthTokenManager // Add tokenManager to the constructor
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email, emailError = null)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password, passwordError = null)
    }

    fun login() {
        if (_uiState.value.email.isBlank()) {
            _uiState.value = _uiState.value.copy(emailError = "Email cannot be empty")
            return
        }
        if (_uiState.value.password.isBlank()) {
            _uiState.value = _uiState.value.copy(passwordError = "Password cannot be empty")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val loginRequest = LoginRequest(
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            val result = authRepository.login(loginRequest)

            result.fold(
                onSuccess = { response ->
                    // Now tokenManager is available to use
                    tokenManager.saveToken(response.accessToken, response.userId)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoginSuccess = true
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Login failed: ${error.message}"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetLoginSuccess() {
        _uiState.value = _uiState.value.copy(isLoginSuccess = false)
    }
}