package com.example.simplelibrarymanagement.presentation.ui.screen.admin.managebook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import com.example.simplelibrarymanagement.domain.repository.CategoryRepository // DIASUMSIKAN ADA
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
        // Mengambil data awal (buku dan kategori) saat ViewModel dibuat
        loadInitialData()
    }

    // DIUBAH: Mengambil buku dan kategori secara bersamaan
    fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                // Mengambil data dari kedua repositori
                val books = bookRepository.getAllBooks()
                val categories = categoryRepository.getAllCategories() // Memanggil fungsi dari repo kategori

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    books = books,
                    categories = categories // Menyimpan daftar kategori ke state
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

    // DIUBAH: Logika menyimpan buku menjadi lebih jelas
    fun onBookSave(book: Book) {
        viewModelScope.launch {
            try {
                // Membedakan antara menambah buku baru atau mengedit
                if (_uiState.value.selectedBook == null) {
                    // Logika untuk menambah buku baru
                    bookRepository.addBook(book)
                } else {
                    // Logika untuk mengedit buku yang sudah ada
                    bookRepository.updateBook(book)
                }
                onDialogDismiss()
                loadInitialData() // Muat ulang data untuk menampilkan perubahan
            } catch(e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Failed to save book.")
            }
        }
    }

    fun onDeleteBook(bookId: String) {
        viewModelScope.launch {
            try {
                bookRepository.deleteBook(bookId)
                loadInitialData() // Muat ulang data
            } catch(e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Failed to delete book.")
            }
        }
    }
}