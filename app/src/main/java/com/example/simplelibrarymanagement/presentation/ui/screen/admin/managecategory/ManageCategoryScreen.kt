package com.example.simplelibrarymanagement.presentation.ui.screen.admin.managecategory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplelibrarymanagement.presentation.ui.component.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategoryScreen(
    viewModel: ManageCategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::onAddNewCategoryClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Category")
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
                "Manage Categories",
                style = MaterialTheme.typography.headlineMedium
            )

            when {
                uiState.isLoading -> LoadingScreen()
                uiState.errorMessage != null -> NetworkErrorMessage(message = uiState.errorMessage!!)
                uiState.categories.isEmpty() -> EmptyState(title = "No Categories", description = "Add a new category to get started.")
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.categories, key = { it.id }) { category ->
                            CardCategory(
                                category = category,
                                onEditClick = { viewModel.onEditCategoryClick(category) },
                                onDeleteClick = { viewModel.onDeleteCategoryRequest(category) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (uiState.showDialog) {
        DialogManageCategory(
            category = uiState.categoryToEdit,
            onDismiss = viewModel::onDialogDismiss,
            onConfirm = viewModel::onCategorySave
        )
    }

    uiState.categoryToDelete?.let { category ->
        AlertDialog(
            onDismissRequest = viewModel::onDismissDeleteDialog,
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete the category '${category.name}'?") },
            confirmButton = {
                Button(
                    onClick = viewModel::onConfirmCategoryDelete,
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
}
