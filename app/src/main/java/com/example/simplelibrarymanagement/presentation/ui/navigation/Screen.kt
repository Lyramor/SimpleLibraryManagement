package com.example.simplelibrarymanagement.presentation.ui.navigation

sealed class Screen(val route: String) {
    data object Auth : Screen("auth_screen")
    data object Login : Screen("login_screen")
    data object Register : Screen("register_screen")
    // Add other screens here as your app grows
    // data object Home : Screen("home_screen")
}
