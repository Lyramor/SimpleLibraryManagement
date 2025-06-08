package com.example.simplelibrarymanagement.presentation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.simplelibrarymanagement.R
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen

/**
 * Sealed class untuk mendefinisikan setiap item yang akan ditampilkan
 * di Bottom Navigation Bar Admin.
 */
sealed class AdminBottomNavItem(
    val route: String,
    val titleResId: Int,
    val icon: ImageVector
) {
    object Dashboard : AdminBottomNavItem(
        route = Screen.AdminDashboard.route,
        titleResId = R.string.admin_nav_dashboard,
        icon = Icons.Default.Dashboard
    )
    object ManageBooks : AdminBottomNavItem(
        route = Screen.AdminManageBooks.route,
        titleResId = R.string.admin_nav_manage_books,
        icon = Icons.Default.Book
    )
    object ManageUsers : AdminBottomNavItem(
        route = Screen.AdminManageUsers.route,
        titleResId = R.string.admin_nav_manage_users,
        icon = Icons.Default.Group
    )
}

/**
 * Composable untuk menampilkan Bottom Navigation Bar khusus Admin.
 *
 * @param navController NavController untuk menangani aksi navigasi.
 * @param items Daftar [AdminBottomNavItem] yang akan ditampilkan.
 */
@Composable
fun AdminBottomNavigationBar(
    navController: NavController,
    items: List<AdminBottomNavItem>
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = stringResource(id = item.titleResId)) },
                label = { Text(text = stringResource(id = item.titleResId)) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary, // Warna berbeda untuk admin
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
