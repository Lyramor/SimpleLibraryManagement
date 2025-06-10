package com.example.simplelibrarymanagement.presentation.ui.screen.admin.manageuser

import com.example.simplelibrarymanagement.domain.model.User

data class ManageUserUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val userToDelete: User? = null,

    // BARU: State untuk dialog tambah/edit
    val showUserDialog: Boolean = false,
    val userToEdit: User? = null
)