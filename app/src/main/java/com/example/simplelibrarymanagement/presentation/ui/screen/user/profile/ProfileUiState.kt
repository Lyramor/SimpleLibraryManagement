package com.example.simplelibrarymanagement.presentation.ui.screen.user.profile

/**
 * Data class yang merepresentasikan state UI untuk layar profile
 */
data class ProfileUiState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null,
    val borrowedBooks: List<BorrowedBook> = emptyList(),
    val borrowHistory: List<BorrowHistory> = emptyList(),
    val errorMessage: String? = null,
    val isLogoutDialogVisible: Boolean = false,
    val isEditProfileDialogVisible: Boolean = false,
    val isUpdatingProfile: Boolean = false,
    val updateProfileSuccess: Boolean = false
)

/**
 * Data class untuk informasi profil pengguna
 */
data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String?,
    val address: String?,
    val joinDate: String,
    val totalBorrowedBooks: Int = 0,
    val activeBorrowedBooks: Int = 0,
    val overdueBooks: Int = 0,
    val profilePictureUrl: String? = null
)

/**
 * Data class untuk buku yang sedang dipinjam
 */
data class BorrowedBook(
    val id: String,
    val bookId: String,
    val bookTitle: String,
    val bookAuthor: String,
    val bookCoverUrl: String?,
    val borrowDate: String,
    val dueDate: String,
    val categoryName: String?, // DIUBAH: Menambahkan kategori
    val isOverdue: Boolean = false
)

/**
 * Data class untuk riwayat peminjaman
 */
data class BorrowHistory(
    val id: String,
    val bookId: String,
    val bookTitle: String,
    val bookAuthor: String,
    val borrowDate: String,
    val returnDate: String?,
    val dueDate: String,
    val status: BorrowStatus
)

/**
 * Enum untuk status peminjaman
 */
enum class BorrowStatus {
    ACTIVE,
    RETURNED,
    OVERDUE,
    RETURNED_LATE
}
