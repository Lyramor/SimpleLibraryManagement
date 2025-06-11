package com.example.simplelibrarymanagement.presentation.ui.screen.admin.manageuser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplelibrarymanagement.presentation.ui.component.CardUser
import com.example.simplelibrarymanagement.presentation.ui.component.DialogManageUser
import com.example.simplelibrarymanagement.presentation.ui.component.EmptyState
import com.example.simplelibrarymanagement.presentation.ui.component.LoadingScreen
import com.example.simplelibrarymanagement.presentation.ui.component.NetworkErrorMessage
import com.example.simplelibrarymanagement.presentation.ui.component.SearchTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageUserScreen(
    viewModel: ManageUserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::onAddNewUserClick,
                // FAB akan menggunakan warna primer (oranye)
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add User")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Manage Users",
                style = MaterialTheme.typography.headlineMedium
            )

            SearchTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChanged,
                placeholder = "Search by name or email...",
                leadingIcon = Icons.Default.Search
            )

            when {
                uiState.isLoading -> LoadingScreen()
                uiState.errorMessage != null -> NetworkErrorMessage(message = uiState.errorMessage!!)
                uiState.users.isEmpty() -> EmptyState(title="No Users", description="There are no users to display.")
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.users, key = { it.id }) { user ->
                            CardUser(
                                name = user.name,
                                email = user.email,
                                role = user.role,
                                onEditClick = { viewModel.onEditUserClick(user) },
                                onDeleteClick = { viewModel.onUserDeleteRequest(user) }
                            )
                        }
                    }
                }
            }
        }
    }

    // DIALOG KONFIRMASI HAPUS
    uiState.userToDelete?.let { user ->
        AlertDialog(
            onDismissRequest = viewModel::onDismissDeleteDialog,
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete the user '${user.name}'?") },
            confirmButton = {
                Button(
                    onClick = viewModel::onConfirmUserDelete,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = viewModel::onDismissDeleteDialog) {
                    Text("Cancel")
                }
            }
        )
    }

    if (uiState.showUserDialog) {
        DialogManageUser(
            user = uiState.userToEdit,
            onDismiss = viewModel::onUserDialogDismiss,
            onConfirm = viewModel::onUserSave
        )
    }
}