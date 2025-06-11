package com.example.simplelibrarymanagement.presentation.ui.screen.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.data.remote.AuthTokenManager // Import AuthTokenManager
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import com.example.simplelibrarymanagement.domain.repository.UserRepository // Import UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository, // Injected UserRepository
    private val tokenManager: AuthTokenManager // Injected AuthTokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomePageData()
    }

    fun loadHomePageData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                // Fetch current user ID from token manager
                val currentUserId = tokenManager.userId

                // Fetch user data if ID is available
                val userNameToDisplay = if (currentUserId != null) {
                    val user = userRepository.getUserById(currentUserId) // Fetch user by ID
                    user?.name ?: "Guest" // Use user's name or "Guest" if not found
                } else {
                    "Guest" // Default to "Guest" if no user ID is present
                }

                // Simulate API call to get all books
                delay(1500) // Simulate network latency
                val allBooks = bookRepository.getAllBooks()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    userName = userNameToDisplay, // Use the fetched or default user name
                    featuredBooks = allBooks.shuffled().take(5), // Random books for "Featured"
                    newArrivals = allBooks.take(5) // Latest books for "New Arrivals"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load home page data. Please try again."
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}