package com.example.simplelibrarymanagement.presentation.ui.screen.admin.manageuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.domain.model.User
import com.example.simplelibrarymanagement.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageUserUiState())
    val uiState: StateFlow<ManageUserUiState> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val users = userRepository.getUsers()
                _uiState.value = _uiState.value.copy(isLoading = false, users = users)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load users."
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            val allUsers = userRepository.getUsers()
            val filteredUsers = allUsers.filter {
                it.name.contains(query, ignoreCase = true) || it.email.contains(query, ignoreCase = true)
            }
            _uiState.value = _uiState.value.copy(searchQuery = query, users = filteredUsers)
        }
    }

    // --- ADDED: LOGIC FOR ADD/EDIT DIALOG ---
    fun onAddNewUserClick() {
        _uiState.value = _uiState.value.copy(showUserDialog = true, userToEdit = null)
    }

    fun onEditUserClick(user: User) {
        _uiState.value = _uiState.value.copy(showUserDialog = true, userToEdit = user)
    }

    fun onUserDialogDismiss() {
        _uiState.value = _uiState.value.copy(showUserDialog = false, userToEdit = null)
    }

    fun onUserSave(user: User) {
        viewModelScope.launch {
            try {
                if (_uiState.value.userToEdit == null) {
                    userRepository.addUser(user)
                } else {
                    userRepository.updateUser(user)
                }
                onUserDialogDismiss()
                loadUsers()
            } catch(e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Failed to save user.")
            }
        }
    }
    // -----------------------------------------

    // --- Logic for delete dialog ---
    fun onUserDeleteRequest(user: User) {
        _uiState.value = _uiState.value.copy(userToDelete = user)
    }

    fun onConfirmUserDelete() {
        viewModelScope.launch {
            val user = _uiState.value.userToDelete
            user?.let {
                try {
                    userRepository.deleteUser(it.id)
                    _uiState.value = _uiState.value.copy(userToDelete = null)
                    loadUsers()
                } catch(e: Exception) {
                    _uiState.value = _uiState.value.copy(errorMessage = "Failed to delete user.")
                }
            }
        }
    }

    fun onDismissDeleteDialog() {
        _uiState.value = _uiState.value.copy(userToDelete = null)
    }
}