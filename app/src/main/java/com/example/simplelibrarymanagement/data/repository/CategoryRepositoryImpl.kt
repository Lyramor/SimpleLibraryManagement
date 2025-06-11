package com.example.simplelibrarymanagement.data.repository

import com.example.simplelibrarymanagement.data.remote.ApiService
import com.example.simplelibrarymanagement.domain.model.Category
import com.example.simplelibrarymanagement.domain.repository.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CategoryRepository {

    override suspend fun getAllCategories(): List<Category> {
        return apiService.getAllCategories()
    }

    // --- IMPLEMENT THE NEW FUNCTIONS ---
    override suspend fun addCategory(category: Category) {
        // We only send the name, as the backend will generate the ID.
        // The backend should return the full new category object.
        apiService.addCategory(category)
    }

    override suspend fun updateCategory(category: Category) {
        apiService.updateCategory(category.id, category)
    }

    override suspend fun deleteCategory(categoryId: Int) {
        apiService.deleteCategory(categoryId)
    }
    // ------------------------------------
}