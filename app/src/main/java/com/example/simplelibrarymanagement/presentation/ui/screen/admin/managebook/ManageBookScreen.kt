package com.example.simplelibrarymanagement.presentation.ui.screen.admin.managebook

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.presentation.ui.component.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageBookScreen(
    viewModel: ManageBookViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::onAddNewBookClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Book")
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
                "Manage Books",
                style = MaterialTheme.typography.headlineMedium
            )

            SearchTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChanged,
                placeholder = "Search by title or author...",
                leadingIcon = Icons.Default.Search
            )

            when {
                uiState.isLoading -> BookListSkeleton()
                uiState.errorMessage != null -> NetworkErrorMessage(message = uiState.errorMessage!!)
                uiState.books.isEmpty() && uiState.searchQuery.isNotBlank() -> {
                    EmptyState(title = "Not Found", description = "No books match your search query.")
                }
                uiState.books.isEmpty() -> {
                    EmptyState(title = "No Books", description = "There are no books in the library yet.")
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.books, key = { it.id }) { book ->
                            AdminCardBook(
                                book = book,
                                onEditClick = { viewModel.onEditBookClick(book) },
                                onDeleteClick = { viewModel.onDeleteBook(book.id) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (uiState.showDialog) {
        DialogManageBook(
            book = uiState.selectedBook,
            categories = uiState.categories,
            onDismiss = viewModel::onDialogDismiss,
            onConfirm = viewModel::onBookSave
        )
    }
}

@Composable
fun AdminCardBook(
    book: Book,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(end = 8.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                CardBook(
                    title = book.title,
                    author = book.author,
                    imageUrl = book.imageUrl,
                    status = if (book.isAvailable) BookStatus.Available else BookStatus.Borrowed,
                    category = book.category,
                    onClick = { /* Admin mungkin tidak butuh aksi klik di sini */ },
                    // --- THIS IS THE FIX ---
                    // Add the missing parameter with an empty action
                    onCategoryClick = { }
                )
            }

            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More Options")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(text = { Text("Edit") }, onClick = {
                        onEditClick()
                        showMenu = false
                    })
                    DropdownMenuItem(text = { Text("Delete") }, onClick = {
                        onDeleteClick()
                        showMenu = false
                    })
                }
            }
        }
    }
}