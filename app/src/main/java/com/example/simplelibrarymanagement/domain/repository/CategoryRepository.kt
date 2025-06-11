package com.example.simplelibrarymanagement.domain.repository

import com.example.simplelibrarymanagement.domain.model.Category

/**
 * Interface for how to get category data.
 */
interface CategoryRepository {
    suspend fun getAllCategories(): List<Category>

    // Add the new function definitions
    suspend fun addCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(categoryId: Int)
}