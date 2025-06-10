package com.example.simplelibrarymanagement.presentation.ui.screen.user.bookbycategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookByCategoryViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle // Untuk mengambil argumen navigasi
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookByCategoryUiState())
    val uiState: StateFlow<BookByCategoryUiState> = _uiState.asStateFlow()

    // Mengambil categoryId dan categoryName dari argumen navigasi
    private val categoryId: Int = checkNotNull(savedStateHandle["categoryId"]).toString().toInt()
    private val categoryName: String = checkNotNull(savedStateHandle["categoryName"])

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                categoryName = categoryName,
                errorMessage = null
            )
            try {
                // Asumsi ada fungsi getBooksByCategoryId di BookRepository
                val books = bookRepository.getBooksByCategoryId(categoryId)
                _uiState.value = _uiState.value.copy(isLoading = false, books = books)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load books for this category."
                )
            }
        }
    }
}
