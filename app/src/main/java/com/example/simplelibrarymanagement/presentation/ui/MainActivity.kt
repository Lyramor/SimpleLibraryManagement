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
import androidx.navigation.navigation
// --- Add missing imports ---
import com.example.simplelibrarymanagement.presentation.ui.screen.user.bookdetail.BookDetailScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.user.main.MainUserScreen
// -------------------------
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen
import com.example.simplelibrarymanagement.presentation.ui.screen.auth.AuthScreen
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
            startDestination = Screen.AuthGraph.route
        ) {
            // Authentication Graph
            navigation(
                startDestination = Screen.Auth.route,
                route = Screen.AuthGraph.route
            ) {
                composable(Screen.Auth.route) {
                    AuthScreen(
                        onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                        onNavigateToRegister = { navController.navigate(Screen.Register.route) }
                    )
                }
                composable(Screen.Login.route) {
                    LoginScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                        onLoginSuccess = {
                            navController.navigate(Screen.UserGraph.route) {
                                popUpTo(Screen.AuthGraph.route) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable(Screen.Register.route) {
                    RegisterScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onNavigateToLogin = { navController.popBackStack() },
                        onRegisterSuccess = { navController.popBackStack() }
                    )
                }
            }

            // Main User Graph
            navigation(
                startDestination = Screen.UserMain.route,
                route = Screen.UserGraph.route
            ) {
                composable(Screen.UserMain.route) {
                    MainUserScreen(rootNavController = navController)
                }
                composable(
                    route = Screen.UserBookByCategory.route,
                    arguments = listOf(
                        navArgument("categoryId") { type = NavType.IntType },
                        navArgument("categoryName") { type = NavType.StringType }
                    )
                ) {
                    BookByCategoryScreen(navController = navController)
                }

                // --- THIS IS THE FIX ---
                // Add the BookDetailScreen as a destination
                composable(
                    route = Screen.UserBookDetail.route,
                    arguments = listOf(navArgument("bookId") { type = NavType.StringType })
                ) {
                    BookDetailScreen(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
                // -------------------------
            }
        }
    }
}