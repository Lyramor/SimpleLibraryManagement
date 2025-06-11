package com.example.simplelibrarymanagement.domain.repository

import com.example.simplelibrarymanagement.domain.model.Borrow

interface BorrowRepository {
    suspend fun getAllBorrows(): List<Borrow>
}