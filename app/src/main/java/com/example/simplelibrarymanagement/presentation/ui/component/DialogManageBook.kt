package com.example.simplelibrarymanagement.presentation.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplelibrarymanagement.domain.model.Book

@Composable
fun DialogManageBook(
    book: Book?, // Null if adding a new book
    onDismiss: () -> Unit,
    onConfirm: (Book) -> Unit
) {
    var title by remember { mutableStateOf(book?.title ?: "") }
    var author by remember { mutableStateOf(book?.author ?: "") }
    var description by remember { mutableStateOf(book?.description ?: "") }
    var imageUrl by remember { mutableStateOf(book?.imageUrl ?: "") }

    val isFormValid = title.isNotBlank() && author.isNotBlank() && description.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (book == nil) "Add New Book" else "Edit Book") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    isError = title.isBlank()
                )
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Author") },
                    isError = author.isBlank()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    maxLines = 4,
                    isError = description.isBlank()
                )
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newBook = Book(
                        id = book?.id ?: System.currentTimeMillis().toString(),
                        title = title,
                        author = author,
                        description = description,
                        imageUrl = imageUrl,
                        isAvailable = book?.isAvailable ?: true
                    )
                    onConfirm(newBook)
                },
                enabled = isFormValid
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}