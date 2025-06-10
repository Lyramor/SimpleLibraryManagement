package com.example.simplelibrarymanagement.presentation.ui.screen.user.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.presentation.ui.component.*
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> LoadingScreen(message = "Loading your library...")
        uiState.errorMessage != null -> NetworkErrorMessage(
            message = uiState.errorMessage ?: "An error occurred.",
            onRetry = viewModel::loadHomePageData
        )
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = "Hello, ${uiState.userName}!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "What book do you want to read today?",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        SearchTextField(
                            value = "",
                            onValueChange = { /* Handle search */ },
                            placeholder = "Search for books...",
                            leadingIcon = Icons.Default.Search
                        )
                    }
                }

                item {
                    BookCarouselSection(
                        title = "Featured Books",
                        books = uiState.featuredBooks,
                        onBookClick = { bookId ->
                            navController.navigate(Screen.UserBookDetail.createRoute(bookId))
                        }
                    )
                }

                item {
                    BookCarouselSection(
                        title = "New Arrivals",
                        books = uiState.newArrivals,
                        onBookClick = { bookId ->
                            navController.navigate(Screen.UserBookDetail.createRoute(bookId))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BookCarouselSection(
    title: String,
    books: List<Book>,
    onBookClick: (String) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (books.isEmpty()) {
            EmptyState(title = "No Books Found", description = "This section is empty for now.")
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(books, key = { it.id }) { book ->
                    CardBook(
                        title = book.title,
                        author = book.author,
                        imageUrl = book.imageUrl,
                        status = if (book.isAvailable) BookStatus.Available else BookStatus.Borrowed,
                        category = book.category, // DIUBAH: Teruskan informasi kategori
                        onClick = { onBookClick(book.id) },
                        modifier = Modifier.width(280.dp)
                    )
                }
            }
        }
    }
}