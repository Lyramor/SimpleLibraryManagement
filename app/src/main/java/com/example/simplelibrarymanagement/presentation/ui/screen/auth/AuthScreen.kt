package com.example.simplelibrarymanagement.presentation.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplelibrarymanagement.presentation.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top navigation tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Login / Sign up",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Login",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        // App Logo and Title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 80.dp)
        ) {
            // App Title
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Boogu",
                    style = MaterialTheme.libraryTypography.AppTitle,
                    color = TextPrimary
                )

                // Orange dot
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            color = Primary,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // App Subtitle
            Text(
                text = "Your book\nlibrary",
                style = MaterialTheme.libraryTypography.AppSubtitle,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }

        // Authentication Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Log in Button
            Button(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = OnPrimary
                ),
                shape = MaterialTheme.libraryShapes.ButtonMedium
            ) {
                Text(
                    text = "Log in",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            // Sign up Button
            OutlinedButton(
                onClick = onNavigateToRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Primary,
                    containerColor = Color.Transparent
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp
                ),
                shape = MaterialTheme.libraryShapes.ButtonMedium
            ) {
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom indicator line
        Box(
            modifier = Modifier
                .width(134.dp)
                .height(4.dp)
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(2.dp)
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    SimpleLibraryManagementTheme {
        AuthScreen()
    }
}