package com.example.simplelibrarymanagement.data.repository

import com.example.simplelibrarymanagement.data.remote.ApiService
import com.example.simplelibrarymanagement.data.remote.BorrowRequest
import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : BookRepository {

    override suspend fun getBookById(bookId: String): Book? { // Changed to String
        return apiService.getBookById(bookId)
    }

    override suspend fun getAllBooks(): List<Book> {
        return apiService.getAllBooks()
    }

    override suspend fun borrowBook(bookId: String, userId: String) { // Changed to String
        try {
            val request = BorrowRequest(bookId = bookId, userId = userId)
            apiService.borrowBook(request)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun addBook(book: Book) {
        apiService.addBook(book)
    }

    override suspend fun updateBook(book: Book) {
        // book.id is now a String again
        apiService.updateBook(book.id, book)
    }

    override suspend fun deleteBook(bookId: String) { // Changed to String
        apiService.deleteBook(bookId)
    }

    override suspend fun getBooksByCategoryId(categoryId: Int): List<Book> {
        val allBooks = apiService.getAllBooks()
        return allBooks.filter { it.category?.id == categoryId }
    }
}