package com.example.simplelibrarymanagement.presentation.ui.screen.user.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData()
    }

    /**
     * Memuat data profil pengguna
     */
    fun loadProfileData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                // Simulasi API call
                delay(1000)

                // Mock data - dalam implementasi sebenarnya, ambil dari repository
                val userProfile = UserProfile(
                    id = "user_001",
                    name = "John Doe",
                    email = "john.doe@example.com",
                    phoneNumber = "+62 812-3456-7890",
                    address = "Jl. Contoh No. 123, Jakarta",
                    joinDate = "15 Januari 2023",
                    totalBorrowedBooks = 25,
                    activeBorrowedBooks = 3,
                    overdueBooks = 1
                )

                val borrowedBooks = listOf(
                    BorrowedBook(
                        id = "borrow_001",
                        bookId = "book_001",
                        bookTitle = "The Great Gatsby",
                        bookAuthor = "F. Scott Fitzgerald",
                        bookCoverUrl = null,
                        borrowDate = "01 Juni 2024",
                        dueDate = "15 Juni 2024",
                        isOverdue = true
                    ),
                    BorrowedBook(
                        id = "borrow_002",
                        bookId = "book_002",
                        bookTitle = "To Kill a Mockingbird",
                        bookAuthor = "Harper Lee",
                        bookCoverUrl = null,
                        borrowDate = "05 Juni 2024",
                        dueDate = "19 Juni 2024",
                        isOverdue = false
                    ),
                    BorrowedBook(
                        id = "borrow_003",
                        bookId = "book_003",
                        bookTitle = "1984",
                        bookAuthor = "George Orwell",
                        bookCoverUrl = null,
                        borrowDate = "10 Juni 2024",
                        dueDate = "24 Juni 2024",
                        isOverdue = false
                    )
                )

                val borrowHistory = listOf(
                    BorrowHistory(
                        id = "history_001",
                        bookId = "book_004",
                        bookTitle = "Pride and Prejudice",
                        bookAuthor = "Jane Austen",
                        borrowDate = "01 Mei 2024",
                        returnDate = "14 Mei 2024",
                        dueDate = "15 Mei 2024",
                        status = BorrowStatus.RETURNED
                    ),
                    BorrowHistory(
                        id = "history_002",
                        bookId = "book_005",
                        bookTitle = "The Catcher in the Rye",
                        bookAuthor = "J.D. Salinger",
                        borrowDate = "15 April 2024",
                        returnDate = "20 April 2024",
                        dueDate = "29 April 2024",
                        status = BorrowStatus.RETURNED_LATE
                    )
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    userProfile = userProfile,
                    borrowedBooks = borrowedBooks,
                    borrowHistory = borrowHistory
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Gagal memuat data profil: ${e.message}"
                )
            }
        }
    }

    /**
     * Menampilkan dialog logout
     */
    fun showLogoutDialog() {
        _uiState.value = _uiState.value.copy(isLogoutDialogVisible = true)
    }

    /**
     * Menyembunyikan dialog logout
     */
    fun hideLogoutDialog() {
        _uiState.value = _uiState.value.copy(isLogoutDialogVisible = false)
    }

    /**
     * Melakukan logout
     */
    fun logout() {
        viewModelScope.launch {
            try {
                // Simulasi proses logout
                delay(500)

                // Dalam implementasi sebenarnya, hapus token dan data pengguna
                // authRepository.logout()

                hideLogoutDialog()
                // Navigate to auth screen - akan ditangani di UI
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Gagal logout: ${e.message}",
                    isLogoutDialogVisible = false
                )
            }
        }
    }

    /**
     * Menampilkan dialog edit profil
     */
    fun showEditProfileDialog() {
        _uiState.value = _uiState.value.copy(isEditProfileDialogVisible = true)
    }

    /**
     * Menyembunyikan dialog edit profil
     */
    fun hideEditProfileDialog() {
        _uiState.value = _uiState.value.copy(
            isEditProfileDialogVisible = false,
            updateProfileSuccess = false
        )
    }

    /**
     * Memperbarui profil pengguna
     */
    fun updateProfile(name: String, phoneNumber: String, address: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUpdatingProfile = true)

            try {
                // Simulasi API call
                delay(1000)

                val currentProfile = _uiState.value.userProfile
                if (currentProfile != null) {
                    val updatedProfile = currentProfile.copy(
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
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isUpdatingProfile = false,
                    errorMessage = "Gagal memperbarui profil: ${e.message}"
                )
            }
        }
    }

    /**
     * Menghapus pesan error
     */
    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    /**
     * Refresh data profil
     */
    fun refreshProfile() {
        loadProfileData()
    }
}