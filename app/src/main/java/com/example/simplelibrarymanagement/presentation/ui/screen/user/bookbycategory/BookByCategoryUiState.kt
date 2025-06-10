package com.example.simplelibrarymanagement.presentation.ui.screen.user.bookbycategory

import com.example.simplelibrarymanagement.domain.model.Book

data class BookByCategoryUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val categoryName: String = "",
    val errorMessage: String? = null
)