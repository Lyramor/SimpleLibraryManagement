package com.example.simplelibrarymanagement.presentation.ui.screen.admin.managebook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageBookViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageBookUiState())
    val uiState: StateFlow<ManageBookUiState> = _uiState.asStateFlow()

    init {
        fetchBooks()
    }

    fun fetchBooks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val books = bookRepository.getAllBooks()
                _uiState.value = _uiState.value.copy(isLoading = false, books = books)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load books."
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        // This would be more efficient with a local cache or a smarter repository
        viewModelScope.launch {
            val allBooks = bookRepository.getAllBooks()
            val filtered = allBooks.filter {
                it.title.contains(query, ignoreCase = true) || it.author.contains(query, ignoreCase = true)
            }
            _uiState.value = _uiState.value.copy(searchQuery = query, books = filtered)
        }
    }

    fun onAddNewBookClick() {
        _uiState.value = _uiState.value.copy(showDialog = true, selectedBook = null)
    }

    fun onEditBookClick(book: Book) {
        _uiState.value = _uiState.value.copy(showDialog = true, selectedBook = book)
    }

    fun onDialogDismiss() {
        _uiState.value = _uiState.value.copy(showDialog = false, selectedBook = null)
    }

    fun onBookSave(book: Book) {
        // In a real app, you'd call repository.addBook(book) or repository.updateBook(book)
        // For this example, we just refresh the list from the mock repository
        onDialogDismiss()
        fetchBooks() // Re-fetch to simulate update
    }

    fun onDeleteBook(bookId: String) {
        // In a real app, call repository.deleteBook(bookId)
        fetchBooks() // Re-fetch to simulate update
    }
}