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
 * di Bottom Navigation Bar. Ini membuat manajemen item menjadi lebih rapi dan aman.
 *
 * @property route Rute navigasi yang terkait dengan item.
 * @property titleResId Resource ID untuk judul item.
 * @property icon Ikon yang akan ditampilkan untuk item.
 */
sealed class BottomNavItem(
    val route: String,
    val titleResId: Int,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(
        route = Screen.UserHome.route,
        titleResId = R.string.bottom_nav_home,
        icon = Icons.Default.Home
    )
    data object BookList : BottomNavItem(
        route = Screen.UserBookList.route,
        titleResId = R.string.bottom_nav_books,
        icon = Icons.Default.MenuBook
    )
    data object Profile : BottomNavItem(
        route = Screen.UserProfile.route,
        titleResId = R.string.bottom_nav_profile,
        icon = Icons.Default.AccountCircle
    )
}


/**
 * Composable untuk menampilkan Bottom Navigation Bar.
 *
 * @param navController NavController untuk menangani aksi navigasi.
 * @param items Daftar [BottomNavItem] yang akan ditampilkan.
 */
@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<BottomNavItem> // DIperbaiki: Menggunakan BottomNavItem, bukan AdminBottomNavItem
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
                        // Pop up ke start destination dari graph untuk menghindari penumpukan back stack
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Hindari membuat instance ganda dari destinasi yang sama
                        launchSingleTop = true
                        // Kembalikan state saat memilih kembali item yang sebelumnya dipilih
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