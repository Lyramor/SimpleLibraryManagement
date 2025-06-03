package com.example.simplelibrarymanagement.presentation.ui.screen.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simplelibrarymanagement.presentation.ui.component.CustomButton
import com.example.simplelibrarymanagement.presentation.ui.component.CustomTextField
import com.example.simplelibrarymanagement.presentation.ui.component.PasswordTextField
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen
import com.example.simplelibrarymanagement.presentation.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit = {},
    onRegistrationSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(uiState.isRegistrationSuccess) {
        if (uiState.isRegistrationSuccess) {
            snackbarHostState.showSnackbar(
                message = "Registration Successful!",
                duration = SnackbarDuration.Short
            )
            onRegistrationSuccess()
            viewModel.resetRegistrationSuccess()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Long
            )
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Create Account") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = TextPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Text(
                    text = "Boogu",
                    style = MaterialTheme.libraryTypography.AppTitle.copy(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )
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

            CustomTextField(
                value = uiState.username,
                onValueChange = viewModel::updateUsername,
                label = "Username",
                leadingIcon = Icons.Default.AccountCircle,
                isError = uiState.usernameError != null,
                errorMessage = uiState.usernameError ?: "",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = uiState.email,
                onValueChange = viewModel::updateEmail,
                label = "Email",
                leadingIcon = Icons.Default.Email,
                isError = uiState.emailError != null,
                errorMessage = uiState.emailError ?: "",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                value = uiState.password,
                onValueChange = viewModel::updatePassword,
                label = "Password",
                leadingIcon = Icons.Default.Lock,
                isError = uiState.passwordError != null,
                errorMessage = uiState.passwordError ?: "",
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                value = uiState.confirmPassword,
                onValueChange = viewModel::updateConfirmPassword,
                label = "Confirm Password",
                leadingIcon = Icons.Default.Lock,
                isError = uiState.confirmPasswordError != null,
                errorMessage = uiState.confirmPasswordError ?: "",
                imeAction = ImeAction.Done,
                onImeAction = {
                    focusManager.clearFocus()
                    if (uiState.isFormValid) {
                        viewModel.register()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomButton(
                text = "Sign Up",
                onClick = {
                    focusManager.clearFocus()
                    viewModel.register()
                },
                isLoading = uiState.isLoading,
                enabled = uiState.isFormValid && !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                TextButton(onClick = onNavigateToLogin) {
                    Text(
                        "Log in",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// SOLUSI 1: Preview tanpa navigasi (direkomendasikan untuk preview)
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    SimpleLibraryManagementTheme {
        RegisterScreen(
            onNavigateBack = { /* Preview - tidak melakukan apa-apa */ },
            onRegistrationSuccess = { /* Preview - tidak melakukan apa-apa */ },
            onNavigateToLogin = { /* Preview - tidak melakukan apa-apa */ },
            onRegisterSuccess = { /* Preview - tidak melakukan apa-apa */ }
        )
    }
}

// SOLUSI 2: Preview dengan mock ViewModel (untuk testing state)
@Preview(showBackground = true, name = "Register Screen - Loading State")
@Composable
fun RegisterScreenLoadingPreview() {
    SimpleLibraryManagementTheme {
        // Untuk preview loading state, Anda bisa membuat mock state
        // Tapi ini memerlukan mock ViewModel yang kompleks
        RegisterScreen(
            onNavigateBack = { },
            onRegistrationSuccess = { },
            onNavigateToLogin = { },
            onRegisterSuccess = { }
        )
    }
}

// SOLUSI 3: Preview untuk berbagai state (memerlukan MockViewModel)
@Preview(showBackground = true, name = "Register Screen - Error State")
@Composable
fun RegisterScreenErrorPreview() {
    SimpleLibraryManagementTheme {
        // Untuk menampilkan error state di preview,
        // Anda perlu membuat MockViewModel atau parameter state langsung
        RegisterScreen(
            onNavigateBack = { },
            onRegistrationSuccess = { },
            onNavigateToLogin = { },
            onRegisterSuccess = { }
        )
    }
}