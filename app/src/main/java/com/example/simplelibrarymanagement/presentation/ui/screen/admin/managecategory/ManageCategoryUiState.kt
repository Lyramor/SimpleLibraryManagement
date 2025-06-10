package com.example.simplelibrarymanagement.presentation.ui.screen.admin.managecategory

import com.example.simplelibrarymanagement.domain.model.Category

data class ManageCategoryUiState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val errorMessage: String? = null,
    val showDialog: Boolean = false,
    val categoryToEdit: Category? = null,
    val categoryToDelete: Category? = null
)