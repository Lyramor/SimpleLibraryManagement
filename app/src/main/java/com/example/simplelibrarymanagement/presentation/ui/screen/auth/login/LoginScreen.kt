package com.example.simplelibrarymanagement.presentation.ui.screen.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simplelibrarymanagement.presentation.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateBack: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        // App Logo and Title
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            Text(
                text = "Boogu",
                style = MaterialTheme.libraryTypography.AppTitle.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = TextPrimary
            )

            // Orange dot
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = Primary,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(50.dp)
                    )
                    .offset(x = 2.dp, y = 4.dp)
            )
        }

        // Login Form
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Username Field
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Username",
                    style = MaterialTheme.libraryTypography.FormLabel.copy(
                        fontSize = 16.sp
                    ),
                    color = Primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = viewModel::updateUsername,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = InputBorder,
                        focusedBorderColor = Primary,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        cursorColor = Primary
                    ),
                    shape = MaterialTheme.libraryShapes.InputField,
                    singleLine = true,
                    isError = uiState.usernameError != null
                )

                if (uiState.usernameError != null) {
                    Text(
                        text = uiState.usernameError!!,
                        style = MaterialTheme.libraryTypography.ErrorText,
                        color = Error,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Password Field
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Password",
                    style = MaterialTheme.libraryTypography.FormLabel.copy(
                        fontSize = 16.sp
                    ),
                    color = Primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = viewModel::updatePassword,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = InputBorder,
                        focusedBorderColor = Primary,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        cursorColor = Primary
                    ),
                    shape = MaterialTheme.libraryShapes.InputField,
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = uiState.passwordError != null
                )

                if (uiState.passwordError != null) {
                    Text(
                        text = uiState.passwordError!!,
                        style = MaterialTheme.libraryTypography.ErrorText,
                        color = Error,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Forgot Password
            Text(
                text = "Forgot your password?",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp
                ),
                color = TextSecondary,
                modifier = Modifier
                    .clickable { /* Handle forgot password */ }
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Login Button
            Button(
                onClick = {
                    viewModel.login()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = OnPrimary
                ),
                shape = MaterialTheme.libraryShapes.ButtonMedium,
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        color = OnPrimary,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Log in",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom indicator line
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(5.dp)
                .background(
                    color = Color.Black,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(2.5.dp)
                )
        )
    }

    // Handle UI State Changes
    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            onLoginSuccess()
        }
    }

    // Show error message if any
    if (uiState.errorMessage != null) {
        LaunchedEffect(uiState.errorMessage) {
            // Show snackbar or handle error display
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    SimpleLibraryManagementTheme {
        LoginScreen()
    }
}