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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen
import com.example.simplelibrarymanagement.presentation.ui.screen.auth.AuthScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.auth.login.LoginScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.auth.register.RegisterScreen
import com.example.simplelibrarymanagement.presentation.ui.theme.SimpleLibraryManagementTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // Penting untuk Hilt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen
        installSplashScreen() //

        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge() //

        setContent {
            SimpleLibraryManagementTheme { //
                LibraryApp()
            }
        }
    }
}

@Composable
fun LibraryApp() {
    Surface(
        modifier = Modifier.fillMaxSize(), //
        color = MaterialTheme.colorScheme.background //
    ) {
        // Set up NavController
        val navController = rememberNavController()

        // NavHost defines the navigation graph
        NavHost(
            navController = navController,
            startDestination = Screen.Auth.route // AuthScreen adalah titik awal
        ) {
            composable(Screen.Auth.route) {
                AuthScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onLoginSuccess = {
                        // Arahkan ke layar beranda atau konten aplikasi utama setelah login
                        // Untuk saat ini, kita kembali ke Auth sebagai placeholder
                        // Anda akan menggantinya dengan navigasi aktual ke layar utama aplikasi Anda
                        navController.popBackStack(Screen.Auth.route, inclusive = false)
                        // Contoh: navController.navigate(Screen.Home.route) { popUpTo(Screen.Auth.route) { inclusive = true } }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route) {
                            // Opsional: popUpTo(Screen.Login.route) { inclusive = true } untuk menghapus login dari backstack
                        }
                    }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onRegistrationSuccess = {
                        // Arahkan ke layar login setelah registrasi berhasil
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Auth.route) // Bersihkan back stack hingga AuthScreen
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Auth.route) // Bersihkan back stack hingga AuthScreen
                        }
                    }
                )
            }
            // Tambahkan rute composable lainnya di sini
            // composable(Screen.Home.route) { /* HomeScreen(...) */ }
        }
    }
}