package com.example.simplelibrarymanagement.presentation.ui.screen.auth.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simplelibrarymanagement.presentation.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit = {},
    onResetSuccess: () -> Unit = {},
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    // Handle UI State Changes for navigation
    LaunchedEffect(uiState.isResetSuccess) {
        if (uiState.isResetSuccess) {
            // Show success message first, then navigate after a delay
            delay(2000)
            onResetSuccess()
            viewModel.resetState()
        }
    }

    // Show error message if any
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Long
            )
            viewModel.clearError()
        }
    }

    // Show success message if any
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Long
            )
            viewModel.clearSuccess()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Forgot Password") },
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

            // App Logo and Title
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Text(
                    text = "Boongu",
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

            // Description Text
            Text(
                text = "Don't worry! Enter your email address and we'll send you a link to reset your password.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Reset Password Form
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Email Field
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Email Address",
                        style = MaterialTheme.libraryTypography.FormLabel.copy(
                            fontSize = 16.sp
                        ),
                        color = Primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = viewModel::updateEmail,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = InputBorder,
                            focusedBorderColor = Primary,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            cursorColor = Primary,
                            errorBorderColor = Error,
                            errorLabelColor = Error,
                            errorCursorColor = Error
                        ),
                        shape = MaterialTheme.libraryShapes.InputField,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = uiState.emailError != null,
                        placeholder = {
                            Text(
                                text = "Enter your email address",
                                color = TextTertiary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                    if (uiState.emailError != null) {
                        Text(
                            text = uiState.emailError!!,
                            style = MaterialTheme.libraryTypography.ErrorText,
                            color = Error,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Reset Password Button
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.resetPassword()
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
                            text = "Send Reset Link",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                // Back to Login Button
                TextButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = "Back to Login",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        ),
                        color = Primary
                    )
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
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    SimpleLibraryManagementTheme {
        ForgotPasswordScreen()
    }
}