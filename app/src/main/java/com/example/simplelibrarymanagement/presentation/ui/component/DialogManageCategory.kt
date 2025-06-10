package com.example.simplelibrarymanagement.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.simplelibrarymanagement.domain.model.Category

@Composable
fun DialogManageCategory(
    category: Category?, // Null jika menambah kategori baru
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var categoryName by remember { mutableStateOf(category?.name ?: "") }
    val isFormValid = categoryName.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (category == null) "Add New Category" else "Edit Category") },
        text = {
            Column {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text("Category Name") },
                    singleLine = true,
                    isError = !isFormValid
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(categoryName)
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
