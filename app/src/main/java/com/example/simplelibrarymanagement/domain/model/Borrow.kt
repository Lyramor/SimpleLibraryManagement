package com.example.simplelibrarymanagement.domain.model

data class Borrow(
    val id: String,
    val book: Book,
    val user: User?, // FIXED: Make user nullable
    val loanDate: String,
    val dueDate: String,
    val returnDate: String?,
    val status: String // "BORROWED", "RETURNED", "OVERDUE"
)