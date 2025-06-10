package com.example.simplelibrarymanagement.presentation.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.simplelibrarymanagement.domain.model.Book
import com.example.simplelibrarymanagement.domain.model.Category // DIASUMSIKAN ADA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogManageBook(
    book: Book?, // Null jika menambah buku baru
    categories: List<Category>, // BARU: Daftar kategori untuk dropdown
    onDismiss: () -> Unit,
    onConfirm: (Book) -> Unit
) {
    var title by remember { mutableStateOf(book?.title ?: "") }
    var author by remember { mutableStateOf(book?.author ?: "") }
    var description by remember { mutableStateOf(book?.description ?: "") }
    var imageUrl by remember { mutableStateOf(book?.imageUrl ?: "") }
    var year by remember { mutableStateOf(book?.year?.toString() ?: "") } // BARU

    // State untuk dropdown kategori
    var isCategoryExpanded by remember { mutableStateOf(false) } // BARU
    var selectedCategory by remember { mutableStateOf(book?.category) } // BARU

    val isFormValid = title.isNotBlank() && author.isNotBlank() && description.isNotBlank() && year.isNotBlank() && selectedCategory != null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (book == null) "Add New Book" else "Edit Book") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp) // Beri jarak lebih
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
                // BARU: Input untuk Tahun
                OutlinedTextField(
                    value = year,
                    onValueChange = { if (it.all { char -> char.isDigit() }) year = it },
                    label = { Text("Year") },
                    isError = year.isBlank(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // BARU: Dropdown untuk Kategori
                ExposedDropdownMenuBox(
                    expanded = isCategoryExpanded,
                    onExpandedChange = { isCategoryExpanded = !isCategoryExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory?.name ?: "Select Category",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryExpanded)
                        },
                        modifier = Modifier.menuAnchor(),
                        isError = selectedCategory == null
                    )
                    ExposedDropdownMenu(
                        expanded = isCategoryExpanded,
                        onDismissRequest = { isCategoryExpanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    selectedCategory = category
                                    isCategoryExpanded = false
                                }
                            )
                        }
                    }
                }

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
                        year = year.toIntOrNull(), // DIUBAH
                        description = description,
                        imageUrl = imageUrl,
                        isAvailable = book?.isAvailable ?: true,
                        category = selectedCategory // DIUBAH
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