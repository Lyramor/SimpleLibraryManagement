package com.example.simplelibrarymanagement.presentation.ui.screen.admin.manageuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.simplelibrarymanagement.domain.usecase.user.*
import com.example.simplelibrarymanagement.domain.model.User
import com.example.simplelibrarymanagement.util.Resource

@HiltViewModel
class ManageUserViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageUserUiState())
    val uiState: StateFlow<ManageUserUiState> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            getAllUsersUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val users = result.data ?: emptyList()
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            users = users,
                            filteredUsers = filterUsers(users, _uiState.value.searchQuery, _uiState.value.selectedUserRole)
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message ?: "Terjadi kesalahan saat memuat data pengguna"
                        )
                    }
                }
            }
        }
    }

    fun searchUsers(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredUsers = filterUsers(_uiState.value.users, query, _uiState.value.selectedUserRole)
        )
    }

    fun filterByRole(role: UserRole) {
        _uiState.value = _uiState.value.copy(
            selectedUserRole = role,
            filteredUsers = filterUsers(_uiState.value.users, _uiState.value.searchQuery, role)
        )
    }

    private fun filterUsers(users: List<User>, query: String, role: UserRole): List<User> {
        return users.filter { user ->
            val matchesQuery = query.isEmpty() ||
                    user.name.contains(query, ignoreCase = true) ||
                    user.email.contains(query, ignoreCase = true)

            val matchesRole = when (role) {
                UserRole.ALL -> true
                UserRole.USER -> user.role.equals("USER", ignoreCase = true)
                UserRole.ADMIN -> user.role.equals("ADMIN", ignoreCase = true)
            }

            matchesQuery && matchesRole
        }
    }

    // Delete User Functions
    fun showDeleteDialog(user: User) {
        _uiState.value = _uiState.value.copy(
            isDeleteDialogVisible = true,
            userToDelete = user
        )
    }

    fun hideDeleteDialog() {
        _uiState.value = _uiState.value.copy(
            isDeleteDialogVisible = false,
            userToDelete = null
        )
    }

    fun deleteUser() {
        val userToDelete = _uiState.value.userToDelete ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isDeleteLoading = true)

            deleteUserUseCase(userToDelete.id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isDeleteLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isDeleteLoading = false,
                            isDeleteDialogVisible = false,
                            userToDelete = null,
                            successMessage = "Pengguna berhasil dihapus"
                        )
                        loadUsers() // Reload users after deletion
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isDeleteLoading = false,
                            error = result.message ?: "Gagal menghapus pengguna"
                        )
                    }
                }
            }
        }
    }

    // Edit User Functions
    fun showEditDialog(user: User) {
        _uiState.value = _uiState.value.copy(
            isEditDialogVisible = true,
            userToEdit = user,
            editUserName = user.name,
            editUserEmail = user.email,
            editUserRole = user.role
        )
    }

    fun hideEditDialog() {
        _uiState.value = _uiState.value.copy(
            isEditDialogVisible = false,
            userToEdit = null,
            editUserName = "",
            editUserEmail = "",
            editUserRole = ""
        )
    }

    fun updateEditUserName(name: String) {
        _uiState.value = _uiState.value.copy(editUserName = name)
    }

    fun updateEditUserEmail(email: String) {
        _uiState.value = _uiState.value.copy(editUserEmail = email)
    }

    fun updateEditUserRole(role: String) {
        _uiState.value = _uiState.value.copy(editUserRole = role)
    }

    fun updateUser() {
        val userToEdit = _uiState.value.userToEdit ?: return
        val currentState = _uiState.value

        if (currentState.editUserName.isBlank() || currentState.editUserEmail.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Nama dan email tidak boleh kosong")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isEditLoading = true)

            val updatedUser = userToEdit.copy(
                name = currentState.editUserName.trim(),
                email = currentState.editUserEmail.trim(),
                role = currentState.editUserRole
            )

            updateUserUseCase(updatedUser).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isEditLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isEditLoading = false,
                            isEditDialogVisible = false,
                            userToEdit = null,
                            editUserName = "",
                            editUserEmail = "",
                            editUserRole = "",
                            successMessage = "Pengguna berhasil diperbarui"
                        )
                        loadUsers() // Reload users after update
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isEditLoading = false,
                            error = result.message ?: "Gagal memperbarui pengguna"
                        )
                    }
                }
            }
        }
    }

    // Add User Functions
    fun showAddDialog() {
        _uiState.value = _uiState.value.copy(isAddUserDialogVisible = true)
    }

    fun hideAddDialog() {
        _uiState.value = _uiState.value.copy(
            isAddUserDialogVisible = false,
            newUserName = "",
            newUserEmail = "",
            newUserPassword = "",
            newUserRole = "USER"
        )
    }

    fun updateNewUserName(name: String) {
        _uiState.value = _uiState.value.copy(newUserName = name)
    }

    fun updateNewUserEmail(email: String) {
        _uiState.value = _uiState.value.copy(newUserEmail = email)
    }

    fun updateNewUserPassword(password: String) {
        _uiState.value = _uiState.value.copy(newUserPassword = password)
    }

    fun updateNewUserRole(role: String) {
        _uiState.value = _uiState.value.copy(newUserRole = role)
    }

    fun addUser() {
        val currentState = _uiState.value

        if (currentState.newUserName.isBlank() ||
            currentState.newUserEmail.isBlank() ||
            currentState.newUserPassword.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Semua field harus diisi")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isAddLoading = true)

            createUserUseCase(
                name = currentState.newUserName.trim(),
                email = currentState.newUserEmail.trim(),
                password = currentState.newUserPassword,
                role = currentState.newUserRole
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isAddLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isAddLoading = false,
                            isAddUserDialogVisible = false,
                            newUserName = "",
                            newUserEmail = "",
                            newUserPassword = "",
                            newUserRole = "USER",
                            successMessage = "Pengguna berhasil ditambahkan"
                        )
                        loadUsers() // Reload users after addition
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isAddLoading = false,
                            error = result.message ?: "Gagal menambahkan pengguna"
                        )
                    }
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }
}