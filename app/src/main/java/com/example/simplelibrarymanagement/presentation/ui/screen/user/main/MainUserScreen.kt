package com.example.simplelibrarymanagement.presentation.ui.screen.user.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplelibrarymanagement.presentation.ui.component.UserBottomNavigationBar
import com.example.simplelibrarymanagement.presentation.ui.component.UserBottomNavItem
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen
import com.example.simplelibrarymanagement.presentation.ui.screen.user.booklist.BookListScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.user.home.HomeScreen
import com.example.simplelibrarymanagement.presentation.ui.screen.user.profile.ProfileScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainUserScreen(
    rootNavController: NavController
) {
    val userNavController = rememberNavController()
    val userNavItems = listOf(
        UserBottomNavItem.Home,
        UserBottomNavItem.BookList,
        UserBottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            UserBottomNavigationBar(navControllerUser = userNavController, items = userNavItems)
        }
    ) { innerPadding ->
        NavHost(
            navController = userNavController,
            startDestination = Screen.UserHome.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.UserHome.route) {
                HomeScreen(navController = rootNavController)
            }
            composable(Screen.UserBookList.route) {
                BookListScreen(navController = rootNavController)
            }
            composable(Screen.UserProfile.route) {
                ProfileScreen(
                    onNavigateToAuth = {
                        // Navigate to Auth Graph and clear the entire back stack
                        rootNavController.navigate(Screen.AuthGraph.route) {
                            popUpTo(rootNavController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}