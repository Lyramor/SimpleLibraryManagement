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
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))

        // App Logo and Title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 100.dp)
        ) {
            // App Title
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Boogu",
                    style = MaterialTheme.libraryTypography.AppTitle.copy(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextPrimary
                )

                // Orange dot
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = Primary,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .offset(x = 4.dp, y = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // App Subtitle
            Text(
                text = "Your book\nlibrary",
                style = MaterialTheme.libraryTypography.AppSubtitle.copy(
                    fontSize = 18.sp
                ),
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }

        // Authentication Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Log in Button
            Button(
                onClick = onNavigateToLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = OnPrimary
                ),
                shape = MaterialTheme.libraryShapes.ButtonMedium
            ) {
                Text(
                    text = "Log in",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            // Sign up Button
            OutlinedButton(
                onClick = onNavigateToRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Primary,
                    containerColor = Color.Transparent
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.5.dp
                ),
                shape = MaterialTheme.libraryShapes.ButtonMedium
            ) {
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
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
                    shape = RoundedCornerShape(2.5.dp)
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