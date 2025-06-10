package com.example.simplelibrarymanagement.data.remote

import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.domain.model.Category
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.DELETE

/**
 * Interface yang mendefinisikan semua endpoint API menggunakan Retrofit.
 */
interface ApiService {

    @GET("categories")
    suspend fun getAllCategories(): List<Category>

    @GET("books")
    suspend fun getAllBooks(): List<Book>

    @GET("books/{id}")
    suspend fun getBookById(@Path("id") bookId: String): Book

    @POST("books")
    suspend fun addBook(@Body book: Book): Book

    @PUT("books/{id}")
    suspend fun updateBook(@Path("id") bookId: String, @Body book: Book): Book

    @DELETE("books/{id}")
    suspend fun deleteBook(@Path("id") bookId: String)

    // Anda bisa menambahkan endpoint lain di sini (misal: untuk user, login, dll.)
}