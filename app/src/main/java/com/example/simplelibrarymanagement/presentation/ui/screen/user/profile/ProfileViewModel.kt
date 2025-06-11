package com.example.simplelibrarymanagement.presentation.ui.screen.user.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelibrarymanagement.data.remote.AuthTokenManager
import com.example.simplelibrarymanagement.domain.repository.BorrowRepository
import com.example.simplelibrarymanagement.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val borrowRepository: BorrowRepository,
    private val tokenManager: AuthTokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData()
    }

    fun loadProfileData() {
        // This line causes the error if tokenManager.userId doesn't exist
        val currentUserId = tokenManager.userId
        if (currentUserId == null) {
            _uiState.value = _uiState.value.copy(errorMessage = "User not logged in.")
            return
        }

        // ... The rest of the function ...
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val user = userRepository.getUserById(currentUserId)
                val allBorrows = borrowRepository.getAllBorrows()

                if (user != null) {
                    val userBorrows = allBorrows.filter { it.userId == currentUserId }
                    val totalBorrowed = userBorrows.size
                    val currentlyBorrowed = userBorrows.count { it.returnDate == null }
                    val overdue = userBorrows.count { it.status == "OVERDUE" && it.returnDate == null }
                    val borrowedBookList = userBorrows
                        .filter { it.returnDate == null }
                        .map { borrowRecord ->
                            BorrowedBook(
                                id = borrowRecord.id,
                                bookId = borrowRecord.bookId,
                                bookTitle = "Book ID: ${borrowRecord.bookId}",
                                bookAuthor = "Author details needed",
                                bookCoverUrl = null,
                                borrowDate = borrowRecord.loanDate,
                                dueDate = borrowRecord.dueDate,
                                categoryName = "Category needed",
                                isOverdue = borrowRecord.status == "OVERDUE"
                            )
                        }

                    val userProfile = UserProfile(
                        id = user.id,
                        name = user.name,
                        email = user.email,
                        phoneNumber = "+62 812-3456-7890",
                        address = "Jl. Contoh No. 123, Jakarta",
                        joinDate = "15 Januari 2023",
                        totalBorrowedBooks = totalBorrowed,
                        activeBorrowedBooks = currentlyBorrowed,
                        overdueBooks = overdue
                    )

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        userProfile = userProfile,
                        borrowedBooks = borrowedBookList
                    )
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Failed to load profile.")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Error: ${e.message}")
            }
        }
    }

    fun showLogoutDialog() {
        _uiState.value = _uiState.value.copy(isLogoutDialogVisible = true)
    }

    fun hideLogoutDialog() {
        _uiState.value = _uiState.value.copy(isLogoutDialogVisible = false)
    }

    fun logout() {
        viewModelScope.launch {
            try {
                tokenManager.clearToken()
                hideLogoutDialog()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Gagal logout: ${e.message}",
                    isLogoutDialogVisible = false
                )
            }
        }
    }

    fun showEditProfileDialog() {
        _uiState.value = _uiState.value.copy(isEditProfileDialogVisible = true)
    }

    fun hideEditProfileDialog() {
        _uiState.value = _uiState.value.copy(
            isEditProfileDialogVisible = false,
            updateProfileSuccess = false
        )
    }

    fun updateProfile(name: String, phoneNumber: String, address: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUpdatingProfile = true)
            val updatedProfile = _uiState.value.userProfile?.copy(
                name = name,
                phoneNumber = phoneNumber,
                address = address
            )
            _uiState.value = _uiState.value.copy(
                userProfile = updatedProfile,
                isUpdatingProfile = false,
                updateProfileSuccess = true
            )
        }
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun refreshProfile() {
        loadProfileData()
    }
}