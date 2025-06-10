package com.example.simplelibrarymanagement.data.repository

import com.example.simplelibrarymanagement.data.remote.ApiService
import com.example.simplelibrarymanagement.domain.model.Category
import com.example.simplelibrarymanagement.domain.repository.CategoryRepository
import javax.inject.Inject
import javax.inject.Singleton

// DIUBAH: Nama class diperbaiki dari 'CategoryRepository' menjadi 'CategoryRepositoryImpl'
@Singleton
class CategoryRepositoryImpl @Inject constructor(
    // Inject ApiService untuk melakukan panggilan jaringan
    private val apiService: ApiService
) : CategoryRepository {

    /**
     * Mengambil daftar semua kategori dari sumber data remote (API).
     * Fungsi ini sekarang akan bergantung pada implementasi di ApiService Anda.
     */
    override suspend fun getAllCategories(): List<Category> {
        // Panggil endpoint dari ApiService untuk mendapatkan kategori.
        // Jika terjadi error (misal: koneksi gagal), Retrofit akan melempar exception
        // yang kemudian akan ditangani oleh ViewModel.
        return apiService.getAllCategories()
    }
}
