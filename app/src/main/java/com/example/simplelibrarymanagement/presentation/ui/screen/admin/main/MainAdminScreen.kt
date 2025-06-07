package com.example.simplelibrarymanagement.presentation.ui.screen.admin.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen

/**
 * Composable utama yang menjadi kerangka untuk semua layar admin setelah login.
 *
 * @param rootNavController NavController dari graph utama, digunakan untuk navigasi
 * ke luar dari lingkup admin (misal: logout).
 */
@Composable
fun MainAdminScreen(rootNavController: NavController) {
    // NavController lokal untuk navigasi antar layar di dalam seksi admin.
    val adminNavController = rememberNavController()

    NavHost(
        navController = adminNavController,
        startDestination = Screen.AdminDashboard.route
    ) {
        composable(Screen.AdminDashboard.route) {
            // Ganti dengan Composable AdminDashboardScreen yang sebenarnya nanti
            // AdminDashboardScreen(navController = adminNavController)
            // Placeholder:
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Admin Dashboard Screen")
            }
        }
        composable(Screen.AdminManageBooks.route) {
            // Ganti dengan Composable ManageBooksScreen yang sebenarnya nanti
            // ManageBooksScreen(navController = adminNavController)
            // Placeholder:
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Manage Books Screen")
            }
        }
        composable(Screen.AdminManageUsers.route) {
            // Ganti dengan Composable ManageUsersScreen yang sebenarnya nanti
            // ManageUsersScreen(navController = adminNavController)
            // Placeholder:
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Manage Users Screen")
            }
        }
        // Tambahkan rute admin lainnya di sini jika diperlukan.
    }
}