package com.example.simplelibrarymanagement.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen
import com.example.simplelibrarymanagement.presentation.ui.screen.auth.AuthScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.auth.forgotpassword.ForgotPasswordScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.auth.login.LoginScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.auth.register.RegisterScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.user.bookbycategory.BookByCategoryScreen
import com.example.simplelibrarymanagement.presentation.ui.theme.SimpleLibraryManagementTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleLibraryManagementTheme {
                LibraryApp()
            }
        }
    }
}

@Composable
fun LibraryApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.Auth.route
        ) {
            // ... (composable untuk Auth, Login, Register, dll. tetap sama)

            // BARU: Menambahkan composable untuk halaman Book By Category
            composable(
                route = Screen.UserBookByCategory.route,
                arguments = listOf(
                    navArgument("categoryId") { type = NavType.IntType },
                    navArgument("categoryName") { type = NavType.StringType }
                )
            ) {
                BookByCategoryScreen(navController = navController)
            }

            // ... (composable lain jika ada)
        }
    }
}
