package com.example.simplelibrarymanagement.presentation.ui.screen.admin.manageuser

import com.example.simplelibrarymanagement.domain.model.User

/**
 * UI State untuk halaman Manage User
 */
data class ManageUserUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val filteredUsers: List<User> = emptyList(),
    val searchQuery: String = "",
    val selectedUserRole: UserRole = UserRole.ALL,
    val error: String? = null,
    val isDeleteDialogVisible: Boolean = false,
    val userToDelete: User? = null,
    val isEditDialogVisible: Boolean = false,
    val userToEdit: User? = null,
    val editUserName: String = "",
    val editUserEmail: String = "",
    val editUserRole: String = "",
    val isAddUserDialogVisible: Boolean = false,
    val newUserName: String = "",
    val newUserEmail: String = "",
    val newUserPassword: String = "",
    val newUserRole: String = "USER",
    val isDeleteLoading: Boolean = false,
    val isEditLoading: Boolean = false,
    val isAddLoading: Boolean = false,
    val successMessage: String? = null
)

/**
 * Enum untuk filter role user
 */
enum class UserRole(val displayName: String) {
    ALL("Semua"),
    USER("User"),
    ADMIN("Admin")
}