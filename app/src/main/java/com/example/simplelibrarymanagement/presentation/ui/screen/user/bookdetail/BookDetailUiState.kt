package com.example.simplelibrarymanagement.presentation.ui.screen.user.bookdetail

import com.example.simplelibrarymanagement.domain.model.Book

data class BookDetailUiState(
    val book: Book? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isBookBorrowed: Boolean = false
)