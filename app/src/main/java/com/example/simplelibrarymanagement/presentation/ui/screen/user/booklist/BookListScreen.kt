package com.example.simplelibrarymanagement.presentation.ui.screen.user.booklist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.simplelibrarymanagement.presentation.ui.component.BookListSkeleton
import com.example.simplelibrarymanagement.presentation.ui.component.CardBook
import com.example.simplelibrarymanagement.presentation.ui.component.BookStatus
import com.example.simplelibrarymanagement.presentation.ui.component.EmptyState
import com.example.simplelibrarymanagement.presentation.ui.component.ErrorMessage
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    navController: NavController,
    viewModel: BookListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // Enhanced Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                )
            ) {
                Text(
                    text = "Library Books",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Discover your next great read",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Content Area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            when {
                uiState.isLoading -> {
                    BookListSkeleton()
                }
                uiState.errorMessage != null -> {
                    ErrorMessage(
                        message = uiState.errorMessage ?: "An unknown error occurred.",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                uiState.books.isEmpty() -> {
                    EmptyState(
                        title = "No Books Available",
                        description = "There are currently no books in the library. Please check back later."
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.books, key = { it.id }) { book ->
                            CardBook(
                                title = book.title,
                                author = book.author,
                                imageUrl = book.imageUrl,
                                status = if (book.isAvailable) BookStatus.Available else BookStatus.Borrowed,
                                category = book.category,
                                onClick = {
                                    navController.navigate(Screen.UserBookDetail.createRoute(book.id))
                                },
                                onCategoryClick = { category ->
                                    category.let {
                                        navController.navigate(
                                            Screen.UserBookByCategory.createRoute(it.id, it.name)
                                        )
                                    }
                                }
                            )
                        }

                        // Bottom padding for better scrolling experience
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}