package com.example.simplelibrarymanagement.presentation.ui.navigation

/**
 * Sealed class untuk mendefinisikan rute navigasi yang unik di seluruh aplikasi.
 * Ini membantu menghindari kesalahan pengetikan nama rute.
 */
sealed class Screen(val route: String) {

    // --- Graph Utama ---
    data object AuthGraph : Screen("auth_graph")
    data object UserGraph : Screen("user_graph")
    data object AdminGraph : Screen("admin_graph")

    // --- Layar Autentikasi ---
    data object Auth : Screen("auth_screen")
    data object Login : Screen("login_screen")
    data object Register : Screen("register_screen")
    data object ForgotPassword : Screen("forgot_password_screen")

    // --- Layar Pengguna (User) ---
    data object UserMain : Screen("user_main_screen") // Wrapper untuk bottom nav
    data object UserHome : Screen("user_home_screen")
    data object UserBookList : Screen("user_book_list_screen")
    data object UserProfile : Screen("user_profile_screen")
    // Contoh rute dengan argumen
    data object UserBookDetail : Screen("user_book_detail_screen/{bookId}") {
        fun createRoute(bookId: String) = "user_book_detail_screen/$bookId"
    }

    // --- Layar Admin ---
    data object AdminMain : Screen("admin_main_screen") // Wrapper untuk admin
    data object AdminDashboard : Screen("admin_dashboard_screen")
    data object AdminManageBooks : Screen("admin_manage_books_screen")
    data object AdminManageUsers : Screen("admin_manage_users_screen")
}