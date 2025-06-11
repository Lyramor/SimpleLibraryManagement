package com.example.simplelibrarymanagement.domain.model

import com.google.gson.annotations.SerializedName

data class Category(
    val id: Int,
    val name: String
)

data class Book(
    // --- FIX: Change id back to String ---
    val id: String,
    val title: String,
    val author: String,
    val year: Int?,
    val description: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("isAvailable")
    val isAvailable: Boolean = true,
    val category: Category?
)