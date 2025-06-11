package com.example.simplelibrarymanagement.data.remote

import com.example.simplelibrarymanagement.data.model.LoginRequest
import com.example.simplelibrarymanagement.data.model.LoginResponse
import com.example.simplelibrarymanagement.data.model.RegisterRequest
import com.example.simplelibrarymanagement.data.model.RegisterResponse
// --- THIS IS THE FIX ---
import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.domain.model.Borrow
import com.example.simplelibrarymanagement.domain.model.Category
import com.example.simplelibrarymanagement.domain.model.User
// -------------------------
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // Auth
    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("api/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    // Borrow Endpoint
    @POST("api/borrows")
    suspend fun borrowBook(@Body borrowRequest: BorrowRequest)

    @GET("api/borrows")
    suspend fun getBorrows(): List<Borrow>

    // User Endpoint
    @GET("api/users/{id}")
    suspend fun getUserById(@Path("id") userId: String): User

    // Category Endpoints
    @GET("api/categories")
    suspend fun getAllCategories(): List<Category>

    @POST("api/categories")
    suspend fun addCategory(@Body category: Category): Category

    @PUT("api/categories/{id}")
    suspend fun updateCategory(@Path("id") categoryId: Int, @Body category: Category): Category

    @DELETE("api/categories/{id}")
    suspend fun deleteCategory(@Path("id") categoryId: Int)

    // --- Book Endpoints (Reverted to String) ---
    @GET("api/books")
    suspend fun getAllBooks(): List<Book>

    @GET("api/books/{id}")
    suspend fun getBookById(@Path("id") bookId: String): Book

    @POST("api/books")
    suspend fun addBook(@Body book: Book): Book

    @PUT("api/books/{id}")
    suspend fun updateBook(@Path("id") bookId: String, @Body book: Book): Book

    @DELETE("api/books/{id}")
    suspend fun deleteBook(@Path("id") bookId: String)
}

data class BorrowRequest(
    @SerializedName("book_id")
    val bookId: String,
    @SerializedName("user_id")
    val userId: String
)