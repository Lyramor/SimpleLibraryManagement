package com.example.simplelibrarymanagement.presentation.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simplelibrarymanagement.domain.model.User

@OptIn(ExperimentalMaterial3Api::class) // Added for ExposedDropdownMenuBox
@Composable
fun DialogManageUser(
    user: User?, // Null jika menambah user baru
    onDismiss: () -> Unit,
    onConfirm: (User) -> Unit
) {
    var name by remember { mutableStateOf(user?.name ?: "") }
    // FIX: Changed "mutableState of" to "mutableStateOf"
    var email by remember { mutableStateOf(user?.email ?: "") }

    // Untuk role, kita bisa gunakan dropdown
    val roles = listOf("Admin", "User")
    var selectedRole by remember { mutableStateOf(user?.role ?: "User") }
    var isRoleExpanded by remember { mutableStateOf(false) }

    val isFormValid = name.isNotBlank() && email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (user == null) "Add New User" else "Edit User") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    isError = name.isBlank()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    isError = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                )

                // Dropdown untuk Role
                ExposedDropdownMenuBox(
                    expanded = isRoleExpanded,
                    onExpandedChange = { isRoleExpanded = !isRoleExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedRole,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Role") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isRoleExpanded)
                        },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = isRoleExpanded,
                        onDismissRequest = { isRoleExpanded = false }
                    ) {
                        roles.forEach { role ->
                            DropdownMenuItem(
                                text = { Text(role) },
                                onClick = {
                                    selectedRole = role
                                    isRoleExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newUser = User(
                        id = user?.id ?: "user_${System.currentTimeMillis()}",
                        name = name,
                        email = email,
                        role = selectedRole
                    )
                    onConfirm(newUser)
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