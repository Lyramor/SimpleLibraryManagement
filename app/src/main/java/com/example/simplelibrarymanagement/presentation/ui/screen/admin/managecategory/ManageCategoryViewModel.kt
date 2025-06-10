package com.example.simplelibrarymanagement.presentation.ui.screen.admin.managecategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.domain.model.Category
import com.example.simplelibrarymanagement.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageCategoryUiState())
    val uiState: StateFlow<ManageCategoryUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                // Asumsi CategoryRepository.getAllCategories() sudah ada
                val categories = categoryRepository.getAllCategories()
                _uiState.value = _uiState.value.copy(isLoading = false, categories = categories)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load categories."
                )
            }
        }
    }

    fun onAddNewCategoryClick() {
        _uiState.value = _uiState.value.copy(showDialog = true, categoryToEdit = null)
    }

    fun onEditCategoryClick(category: Category) {
        _uiState.value = _uiState.value.copy(showDialog = true, categoryToEdit = category)
    }

    fun onDialogDismiss() {
        _uiState.value = _uiState.value.copy(showDialog = false, categoryToEdit = null)
    }

    fun onCategorySave(categoryName: String) {
        viewModelScope.launch {
            try {
                val categoryToEdit = _uiState.value.categoryToEdit
                if (categoryToEdit == null) {
                    // Asumsi addCategory ada di repo
                    // ID akan di-generate oleh database di backend
                    val newCategory = Category(id = 0, name = categoryName)
                    categoryRepository.addCategory(newCategory)
                } else {
                    // Asumsi updateCategory ada di repo
                    val updatedCategory = categoryToEdit.copy(name = categoryName)
                    categoryRepository.updateCategory(updatedCategory)
                }
                onDialogDismiss()
                loadCategories()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Failed to save category.")
            }
        }
    }

    fun onDeleteCategoryRequest(category: Category) {
        _uiState.value = _uiState.value.copy(categoryToDelete = category)
    }

    fun onConfirmCategoryDelete() {
        viewModelScope.launch {
            _uiState.value.categoryToDelete?.let {
                try {
                    // Asumsi deleteCategory ada di repo
                    categoryRepository.deleteCategory(it.id)
                    _uiState.value = _uiState.value.copy(categoryToDelete = null)
                    loadCategories()
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(errorMessage = "Failed to delete category.")
                }
            }
        }
    }

    fun onDismissDeleteDialog() {
        _uiState.value = _uiState.value.copy(categoryToDelete = null)
    }
}