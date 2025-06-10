package com.example.simplelibrarymanagement.presentation.ui.screen.admin.managebook

import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.domain.model.Category

data class ManageBookUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val categories: List<Category> = emptyList(),
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val showDialog: Boolean = false,
    val selectedBook: Book? = null
)
