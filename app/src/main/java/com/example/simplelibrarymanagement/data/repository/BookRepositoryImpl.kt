package com.example.simplelibrarymanagement.data.repository

import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor() : BookRepository {

    private val books = mutableListOf(
        Book(
            id = "1",
            title = "Atomic Habits",
            author = "James Clear",
            description = "An easy and proven way to build good habits and break bad ones. Atomic Habits will reshape the way you think about progress and success, and give you the tools and strategies you need to transform your habits.",
            imageUrl = "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1655988385l/40121378.jpg"
        ),
        Book(
            id = "2",
            title = "Sapiens: A Brief History of Humankind",
            author = "Yuval Noah Harari",
            description = "A groundbreaking narrative of humanity’s creation and evolution that explores the ways in which biology and history have defined us and enhanced our understanding of what it means to be 'human.'",
            imageUrl = "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1420585954l/23692271.jpg"
        )
    )

    override suspend fun getBookById(bookId: String): Book? {
        delay(500)
        return books.find { it.id == bookId }
    }

    override suspend fun getAllBooks(): List<Book> {
        delay(1000)
        return books
    }

    override suspend fun borrowBook(bookId: String) {
        delay(1000)
        val bookIndex = books.indexOfFirst { it.id == bookId }
        if (bookIndex != -1) {
            val book = books[bookIndex]
            books[bookIndex] = book.copy(isAvailable = false)
        }
    }
}