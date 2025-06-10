package com.example.simplelibrarymanagement.domain.model

data class Category(
    val id: Int,
    val name: String
)


data class Book(
    val id: String,
    val title: String,
    val author: String,
    val year: Int?,
    val description: String,
    val imageUrl: String,
    val isAvailable: Boolean = true,
    val category: Category?
)