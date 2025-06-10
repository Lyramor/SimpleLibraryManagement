package com.example.simplelibrarymanagement.presentation.ui.screen.user.bookbycategory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.simplelibrarymanagement.presentation.ui.component.*
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookByCategoryScreen(
    navController: NavController,
    viewModel: BookByCategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.categoryName) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> LoadingScreen()
            uiState.errorMessage != null -> NetworkErrorMessage(message = uiState.errorMessage!!)
            uiState.books.isEmpty() -> EmptyState(
                title = "No Books Found",
                description = "There are no books in the '${uiState.categoryName}' category yet."
            )
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.books, key = { it.id }) { book ->
                        CardBook(
                            title = book.title,
                            author = book.author,
                            imageUrl = book.imageUrl,
                            status = if (book.isAvailable) BookStatus.Available else BookStatus.Borrowed,
                            category = book.category,
                            // Aksi klik kategori di sini tidak diperlukan karena sudah di halaman kategori
                            onCategoryClick = {},
                            onClick = {
                                navController.navigate(Screen.UserBookDetail.createRoute(book.id))
                            }
                        )
                    }
                }
            }
        }
    }
}
