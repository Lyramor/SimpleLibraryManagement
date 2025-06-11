package com.example.simplelibrarymanagement.presentation.ui.screen.admin.managebook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import com.example.simplelibrarymanagement.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageBookViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageBookUiState())
    val uiState: StateFlow<ManageBookUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val books = bookRepository.getAllBooks()
                val categories = categoryRepository.getAllCategories()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    books = books,
                    categories = categories
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load data. Please check connection."
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
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
        viewModelScope.launch {
            try {
                if (_uiState.value.selectedBook == null) {
                    bookRepository.addBook(book)
                } else {
                    bookRepository.updateBook(book)
                }
                onDialogDismiss()
                loadInitialData()
            } catch(e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Failed to save book.")
            }
        }
    }

    fun onDeleteBook(bookId: String) { // FIXED: Changed parameter type from Int to String
        viewModelScope.launch {
            try {
                bookRepository.deleteBook(bookId)
                loadInitialData()
            } catch(e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Failed to delete book.")
            }
        }
    }
}