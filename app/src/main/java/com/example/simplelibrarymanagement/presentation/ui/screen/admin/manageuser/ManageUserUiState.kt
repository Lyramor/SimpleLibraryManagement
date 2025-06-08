package com.example.simplelibrarymanagement.presentation.ui.screen.admin.manageuser

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String // e.g., "Admin", "User"
)

data class ManageUserUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val userToDelete: User? = null,
    val userToEdit: User? = null
)