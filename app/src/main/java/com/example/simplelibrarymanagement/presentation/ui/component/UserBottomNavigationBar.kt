package com.example.simplelibrarymanagement.presentation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
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
 * di Bottom Navigation Bar Pengguna.
 */
sealed class UserBottomNavItem(
    val route: String,
    val titleResId: Int,
    val icon: ImageVector
) {
    data object Home : UserBottomNavItem(
        route = Screen.UserHome.route,
        titleResId = R.string.bottom_nav_home,
        icon = Icons.Default.Home
    )
    data object BookList : UserBottomNavItem(
        route = Screen.UserBookList.route,
        titleResId = R.string.bottom_nav_books,
        icon = Icons.Default.MenuBook
    )
    data object Profile : UserBottomNavItem(
        route = Screen.UserProfile.route,
        titleResId = R.string.bottom_nav_profile,
        icon = Icons.Default.AccountCircle
    )
}

/**
 * Composable untuk menampilkan Bottom Navigation Bar Pengguna.
 *
 * @param navControllerUser NavController untuk menangani aksi navigasi pengguna.
 * @param items Daftar [UserBottomNavItem] yang akan ditampilkan.
 */
@Composable
fun UserBottomNavigationBar(
    navControllerUser: NavController,
    items: List<UserBottomNavItem>
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        val navBackStackEntry by navControllerUser.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navControllerUser.navigate(item.route) {
                        navControllerUser.graph.startDestinationRoute?.let { route ->
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
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}