package com.example.simplelibrarymanagement.presentation.ui.screen.user.booklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.simplelibrarymanagement.presentation.ui.component.BookListSkeleton
import com.example.simplelibrarymanagement.presentation.ui.component.CardBook
import com.example.simplelibrarymanagement.presentation.ui.component.BookStatus
import com.example.simplelibrarymanagement.presentation.ui.component.EmptyState
import com.example.simplelibrarymanagement.presentation.ui.component.ErrorMessage
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen

@Composable
fun BookListScreen(
    navController: NavController,
    viewModel: BookListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.books, key = { it.id }) { book ->
                    CardBook(
                        title = book.title,
                        author = book.author,
                        imageUrl = book.imageUrl,
                        status = if (book.isAvailable) BookStatus.Available else BookStatus.Borrowed,
                        category = book.category, // DIUBAH: Teruskan informasi kategori
                        onClick = {
                            navController.navigate(Screen.UserBookDetail.createRoute(book.id))
                        }
                    )
                }
            }
        }
    }
}
