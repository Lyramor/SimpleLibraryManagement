package com.example.simplelibrarymanagement.presentation.ui.screen.user.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplelibrarymanagement.presentation.ui.theme.libraryColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToAuth: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Handle logout navigation
    LaunchedEffect(uiState.isLogoutDialogVisible) {
        // Navigation akan ditangani melalui aksi pada dialog
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ProfileHeader(
                    userProfile = uiState.userProfile,
                    onEditProfile = viewModel::showEditProfileDialog
                )
            }

            item {
                StatsSection(userProfile = uiState.userProfile)
            }

            item {
                Text(
                    text = "Buku yang Sedang Dipinjam",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (uiState.borrowedBooks.isEmpty()) {
                item {
                    EmptyStateCard(
                        message = "Tidak ada buku yang sedang dipinjam",
                        icon = Icons.Default.MenuBook
                    )
                }
            } else {
                items(uiState.borrowedBooks) { borrowedBook ->
                    BorrowedBookCard(borrowedBook = borrowedBook)
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pengaturan",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                SettingsSection(
                    onLogout = viewModel::showLogoutDialog,
                    onRefresh = viewModel::refreshProfile
                )
            }
        }

        // Loading overlay
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

    // Dialogs
    if (uiState.isLogoutDialogVisible) {
        LogoutDialog(
            onConfirm = {
                viewModel.logout()
                onNavigateToAuth()
            },
            onDismiss = viewModel::hideLogoutDialog
        )
    }

    if (uiState.isEditProfileDialogVisible) {
        EditProfileDialog(
            userProfile = uiState.userProfile,
            isLoading = uiState.isUpdatingProfile,
            onSave = viewModel::updateProfile,
            onDismiss = viewModel::hideEditProfileDialog
        )
    }

    // Show error message
    uiState.errorMessage?.let { message ->
        LaunchedEffect(message) {
            // Anda bisa menampilkan Snackbar di sini jika mau
            viewModel.clearErrorMessage()
        }
    }

    // Show success message
    if (uiState.updateProfileSuccess) {
        LaunchedEffect(uiState.updateProfileSuccess) {
            // Anda bisa menampilkan Snackbar sukses di sini jika mau
            viewModel.hideEditProfileDialog()
        }
    }
}

@Composable
private fun ProfileHeader(
    userProfile: UserProfile?,
    onEditProfile: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            userProfile?.let { profile ->
                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = profile.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                profile.phoneNumber?.let { phone ->
                    Text(
                        text = phone,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Bergabung sejak ${profile.joinDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onEditProfile,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edit Profil")
                }
            }
        }
    }
}

@Composable
private fun StatsSection(userProfile: UserProfile?) {
    userProfile?.let { profile ->
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    value = profile.totalBorrowedBooks.toString(),
                    label = "Total Pinjam",
                    color = MaterialTheme.colorScheme.primary
                )
                StatItem(
                    value = profile.activeBorrowedBooks.toString(),
                    label = "Sedang Pinjam",
                    color = MaterialTheme.libraryColors.borrowed
                )
                StatItem(
                    value = profile.overdueBooks.toString(),
                    label = "Terlambat",
                    color = MaterialTheme.libraryColors.overdue
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    color: androidx.compose.ui.graphics.Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun BorrowedBookCard(borrowedBook: BorrowedBook) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    borrowedBook.categoryName?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))
                    }
                    Text(
                        text = borrowedBook.bookTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = borrowedBook.bookAuthor,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    color = if (borrowedBook.isOverdue)
                        MaterialTheme.libraryColors.overdue
                    else
                        MaterialTheme.libraryColors.borrowed,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = if (borrowedBook.isOverdue) "Terlambat" else "Aktif",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Dipinjam: ${borrowedBook.borrowDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Jatuh tempo: ${borrowedBook.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (borrowedBook.isOverdue)
                        MaterialTheme.libraryColors.overdue
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    onLogout: () -> Unit,
    onRefresh: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SettingsItem(
                icon = Icons.Default.Refresh,
                title = "Refresh Data",
                onClick = onRefresh
            )
            SettingsItem(
                icon = Icons.Default.ExitToApp,
                title = "Logout",
                onClick = onLogout,
                isDestructive = true
            )
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDestructive) MaterialTheme.libraryColors.overdue else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isDestructive) MaterialTheme.libraryColors.overdue else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun EmptyStateCard(
    message: String,
    icon: ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LogoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Konfirmasi Logout") },
        text = { Text("Apakah Anda yakin ingin keluar dari aplikasi?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Logout")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}

@Composable
private fun EditProfileDialog(
    userProfile: UserProfile?,
    isLoading: Boolean,
    onSave: (String, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(userProfile?.name ?: "") }
    var phoneNumber by remember { mutableStateOf(userProfile?.phoneNumber ?: "") }
    var address by remember { mutableStateOf(userProfile?.address ?: "") }

    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        title = { Text("Edit Profil") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama") },
                    enabled = !isLoading,
                    singleLine = true
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Nomor Telepon") },
                    enabled = !isLoading,
                    singleLine = true
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Alamat") },
                    enabled = !isLoading,
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(name, phoneNumber, address) },
                enabled = !isLoading && name.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                } else {
                    Text("Simpan")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isLoading
            ) {
                Text("Batal")
            }
        }
    )
}
