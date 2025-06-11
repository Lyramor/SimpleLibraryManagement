package com.example.simplelibrarymanagement.data.repository

import com.example.simplelibrarymanagement.data.remote.ApiService
import com.example.simplelibrarymanagement.domain.model.Borrow
import com.example.simplelibrarymanagement.domain.repository.BorrowRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BorrowRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : BorrowRepository {
    override suspend fun getAllBorrows(): List<Borrow> {
        return try {
            apiService.getBorrows()
        } catch (e: Exception) {
            // Return an empty list if there's an error
            emptyList()
        }
    }
}