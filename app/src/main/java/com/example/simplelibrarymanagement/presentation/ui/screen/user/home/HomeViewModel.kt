package com.example.simplelibrarymanagement.presentation.ui.screen.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomePageData()
    }

    fun loadHomePageData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null) // Diperbaiki dari nil ke null
            try {
                // Simulate API call to get all data
                delay(1500) // Simulate network latency
                val allBooks = bookRepository.getAllBooks()

                // In a real app, you'd fetch user data from a user repository
                val userName = "Lyramor"

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    userName = userName,
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
        _uiState.value = _uiState.value.copy(errorMessage = null) // Diperbaiki dari nil ke null
    }
}