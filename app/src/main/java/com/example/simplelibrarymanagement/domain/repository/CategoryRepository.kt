package com.example.simplelibrarymanagement.domain.repository

import com.example.simplelibrarymanagement.domain.model.Category

/**
 * Interface untuk mendefinisikan kontrak bagaimana cara mendapatkan data kategori.
 * ViewModel akan berinteraksi dengan interface ini, bukan dengan implementasi langsungnya.
 */
interface CategoryRepository {
    /**
     * Mengambil daftar semua kategori dari sumber data.
     */
    suspend fun getAllCategories(): List<Category>
}
