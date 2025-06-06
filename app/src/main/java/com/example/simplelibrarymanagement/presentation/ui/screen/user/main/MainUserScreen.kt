package com.example.simplelibrarymanagement.presentation.ui.screen.user.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.simplelibrarymanagement.presentation.ui.component.BottomNavigationBar
import com.example.simplelibrarymanagement.presentation.ui.component.BottomNavItem
import com.example.simplelibrarymanagement.presentation.ui.navigation.Screen

/**
 * Composable utama yang menjadi kerangka untuk semua layar pengguna setelah login.
 * Mengatur tata letak umum dengan TopAppBar dan BottomNavigationBar.
 *
 * @param rootNavController NavController dari graph utama, digunakan untuk navigasi
 * ke luar dari lingkup pengguna (misal: logout atau ke detail).
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainUserScreen(rootNavController: NavController) {
    // NavController lokal untuk navigasi di dalam MainUserScreen (antara Home, BookList, Profile)
    val userNavController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.BookList,
        BottomNavItem.Profile
    )

    Scaffold(
        topBar = {
            // Judul TopAppBar bisa dibuat dinamis berdasarkan layar yang sedang aktif.
            val navBackStackEntry by userNavController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val currentScreen = bottomNavItems.find { it.route == currentRoute }

            TopAppBar(
                title = { Text(currentScreen?.let { stringResource(id = it.titleResId) } ?: "Library") }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = userNavController, items = bottomNavItems)
        }
    ) { innerPadding ->
        // NavHost untuk konten utama pengguna
        NavHost(
            navController = userNavController,
            startDestination = Screen.UserHome.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.UserHome.route) {
                // Ganti dengan composable HomeScreen yang sebenarnya nanti
                // HomeScreen(navController = rootNavController)
                // Placeholder:
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Home Screen Content")
                }
            }
            composable(Screen.UserBookList.route) {
                // Ganti dengan composable BookListScreen yang sebenarnya nanti
                // BookListScreen(navController = rootNavController)
                // Placeholder:
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Book List Screen Content")
                }
            }
            composable(Screen.UserProfile.route) {
                // Ganti dengan composable ProfileScreen yang sebenarnya nanti
                // ProfileScreen(navController = rootNavController)
                // Placeholder:
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Profile Screen Content")
                }
            }
            // Anda bisa menambahkan rute lain yang diakses dari dalam user graph di sini,
            // contohnya, jika detail buku hanya bisa diakses dari daftar buku, rutenya bisa ada di sini.
            // composable(Screen.UserBookDetail.route) { backStackEntry ->
            //     val bookId = backStackEntry.arguments?.getString("bookId")
            //     BookDetailScreen(bookId = bookId, navController = rootNavController)
            // }
        }
    }
}
