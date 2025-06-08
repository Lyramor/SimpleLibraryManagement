package com.example.simplelibrarymanagement.presentation.ui.screen.user.home

import com.example.simplelibrarymanagement.domain.model.Book

data class HomeUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val featuredBooks: List<Book> = emptyList(),
    val newArrivals: List<Book> = emptyList(),
    val errorMessage: String? = null
)