package com.example.simplelibrarymanagement.presentation.ui.screen.admin.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.simplelibrarymanagement.R
import com.example.simplelibrarymanagement.presentation.ui.component.AdminBottomNavItem
import com.example.simplelibrarymanagement.presentation.ui.component.AdminBottomNavigationBar
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen
import com.example.simplelibrarymanagement.presentation.ui.screen.admin.managebook.ManageBookScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.admin.managecategory.ManageCategoryScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.admin.manageuser.ManageUserScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainAdminScreen(rootNavController: NavController) {
    val adminNavController = rememberNavController()

    // DIUBAH: Menambahkan item navigasi kategori
    val adminNavItems = listOf(
        AdminBottomNavItem.Dashboard,
        AdminBottomNavItem.ManageBooks,
        AdminBottomNavItem.ManageCategories, // Ditambahkan
        AdminBottomNavItem.ManageUsers
    )

    Scaffold(
        topBar = {
            val navBackStackEntry by adminNavController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val currentScreen = adminNavItems.find { it.route == currentRoute }

            TopAppBar(
                title = { Text(currentScreen?.let { stringResource(id = it.titleResId) } ?: "Admin Panel") }
            )
        },
        bottomBar = {
            AdminBottomNavigationBar(navControllerAdmin = adminNavController, items = adminNavItems)
        }
    ) { innerPadding ->
        NavHost(
            navController = adminNavController,
            startDestination = Screen.AdminDashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.AdminDashboard.route) {
                AdminDashboardScreen(navController = adminNavController)
            }
            composable(Screen.AdminManageBooks.route) {
                ManageBookScreen()
            }
            // BARU: Menambahkan composable untuk layar kategori
            composable(Screen.AdminManageCategory.route) {
                ManageCategoryScreen()
            }
            composable(Screen.AdminManageUsers.route) {
                ManageUserScreen()
            }
        }
    }
}

// Placeholder untuk Dashboard Screen
@Composable
fun AdminDashboardScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Admin Dashboard")
    }
}
