package com.example.simplelibrarymanagement.presentation.ui.screen.user.bookdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.data.remote.AuthTokenManager
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val tokenManager: AuthTokenManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    // Define bookId as a class property
    private val bookId: String = savedStateHandle.get<String>("bookId")!!

    init {
        fetchBookDetails(bookId)
    }

    private fun fetchBookDetails(bookId: String) { // FIXED: Changed parameter type from Int to String
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val book = bookRepository.getBookById(bookId)
                _uiState.value = _uiState.value.copy(
                    book = book,
                    isLoading = false,
                    isBookBorrowed = book?.isAvailable == false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to load book details.",
                    isLoading = false
                )
            }
        }
    }

    fun borrowBook() {
        val currentUserId = tokenManager.userId
        if (currentUserId == null) {
            _uiState.value = _uiState.value.copy(errorMessage = "You must be logged in to borrow a book.")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Now the class property 'bookId' can be accessed here
                bookRepository.borrowBook(bookId, currentUserId)
                _uiState.value = _uiState.value.copy(
                    isBookBorrowed = true,
                    isLoading = false
                )
                // And here
                fetchBookDetails(bookId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to borrow the book: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}