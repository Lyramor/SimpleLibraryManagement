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
        // Spacer ini akan mendorong bagian Logo & Judul ke bawah.
        // Rasio weight 0.8f (di atas logo) dan 1.0f (di bawah logo)
        // akan membuat logo sedikit lebih tinggi dari tengah-tengah ruang yang tersedia untuknya.
        Spacer(modifier = Modifier.weight(0.8f))

        // App Logo and Title
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
            // modifier = Modifier.padding(bottom = 80.dp) // Dihapus, diatur oleh Spacer.weight
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
                text = "Your book library",
                style = MaterialTheme.libraryTypography.AppSubtitle.copy(
                    fontSize = 22.sp
                ),
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )
        }

        // Spacer ini akan mendorong tombol-tombol ke bagian bawah.
        Spacer(modifier = Modifier.weight(1.0f))

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

        Spacer(modifier = Modifier.height(32.dp)) // Jarak antara tombol dan garis bawah

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
        Spacer(modifier = Modifier.height(32.dp)) // Margin di bagian paling bawah layar
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    SimpleLibraryManagementTheme {
        AuthScreen()
    }
}
