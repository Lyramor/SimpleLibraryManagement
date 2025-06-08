package com.example.simplelibrarymanagement.presentation.ui.screen.user.booklist

import com.example.simplelibrarymanagement.domain.model.Book

data class BookListUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val errorMessage: String? = null
)