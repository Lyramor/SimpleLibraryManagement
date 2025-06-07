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
            // Tampilkan skeleton loading saat data sedang diambil
            BookListSkeleton()
        }
        uiState.errorMessage != null -> {
            // Tampilkan pesan error jika terjadi masalah
            ErrorMessage(
                message = uiState.errorMessage ?: "An unknown error occurred.",
                modifier = Modifier.fillMaxSize()
            )
        }
        uiState.books.isEmpty() -> {
            // Tampilkan pesan jika tidak ada buku yang tersedia
            EmptyState(
                title = "No Books Available",
                description = "There are currently no books in the library. Please check back later."
            )
        }
        else -> {
            // Tampilkan daftar buku jika data berhasil diambil dan tidak kosong
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
                        onClick = {
                            // Navigasi ke layar detail buku dengan membawa ID buku
                            navController.navigate(Screen.UserBookDetail.createRoute(book.id))
                        }
                    )
                }
            }
        }
    }
}