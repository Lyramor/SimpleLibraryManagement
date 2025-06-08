package com.example.simplelibrarymanagement.presentation.ui.screen.admin.manageuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageUserViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ManageUserUiState())
    val uiState: StateFlow<ManageUserUiState> = _uiState.asStateFlow()

    private val allUsers = mutableListOf<User>()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                // Simulate API call
                delay(1000)
                val users = listOf(
                    User("1", "Alice Johnson", "alice@example.com", "Admin"),
                    User("2", "Bob Williams", "bob@example.com", "User"),
                    User("3", "Charlie Brown", "charlie@example.com", "User"),
                    User("4", "Diana Prince", "diana@example.com", "User"),
                )
                allUsers.clear()
                allUsers.addAll(users)
                _uiState.value = _uiState.value.copy(isLoading = false, users = allUsers)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load users."
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        val filteredUsers = allUsers.filter {
            it.name.contains(query, ignoreCase = true) || it.email.contains(query, ignoreCase = true)
        }
        _uiState.value = _uiState.value.copy(users = filteredUsers)
    }

    fun onUserDeleteRequest(user: User) {
        _uiState.value = _uiState.value.copy(userToDelete = user)
    }

    fun onConfirmUserDelete() {
        val user = _uiState.value.userToDelete
        user?.let {
            allUsers.remove(it)
            onSearchQueryChanged(_uiState.value.searchQuery)
        }
        _uiState.value = _uiState.value.copy(userToDelete = null)
    }

    fun onDismissDeleteDialog() {
        _uiState.value = _uiState.value.copy(userToDelete = null)
    }
}