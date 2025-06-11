package com.example.simplelibrarymanagement.domain.repository

import com.example.simplelibrarymanagement.domain.model.Book

interface BookRepository {
    // --- FIX: Change Int back to String ---
    suspend fun getBookById(bookId: String): Book?
    suspend fun getAllBooks(): List<Book>
    suspend fun borrowBook(bookId: String, userId: String)
    suspend fun addBook(book: Book)
    suspend fun updateBook(book: Book)
    suspend fun deleteBook(bookId: String)
    suspend fun getBooksByCategoryId(categoryId: Int): List<Book>
}