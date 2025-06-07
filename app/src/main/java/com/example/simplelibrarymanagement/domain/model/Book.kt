package com.example.simplelibrarymanagement.domain.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String,
    val isAvailable: Boolean = true
)