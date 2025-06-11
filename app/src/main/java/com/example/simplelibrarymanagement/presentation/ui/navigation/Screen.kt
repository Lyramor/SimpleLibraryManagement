package com.example.simplelibrarymanagement.presentation.ui.navigation

/**
 * Sealed class for defining unique navigation routes.
 */
sealed class Screen(val route: String) {

    // --- Main Graphs ---
    data object AuthGraph : Screen("auth_graph")
    data object UserGraph : Screen("user_graph")
    data object AdminGraph : Screen("admin_graph")

    // --- Auth Screens ---
    data object Auth : Screen("auth_screen")
    data object Login : Screen("login_screen")
    data object Register : Screen("register_screen")

    // --- User Screens ---
    data object UserMain : Screen("user_main_screen")
    data object UserHome : Screen("user_home_screen")
    data object UserBookList : Screen("user_book_list_screen")
    data object UserProfile : Screen("user_profile_screen")

    // --- THIS IS THE FIX ---
    // The NavHost expects bookId to be an Int, so createRoute must accept an Int.
    data object UserBookDetail : Screen("user_book_detail_screen/{bookId}") {
        // --- FIX: Change Int back to String ---
        fun createRoute(bookId: String) = "user_book_detail_screen/$bookId"
    }
    // -------------------------

    data object UserBookByCategory : Screen("user_book_by_category_screen/{categoryId}/{categoryName}") {
        fun createRoute(categoryId: Int, categoryName: String) = "user_book_by_category_screen/$categoryId/$categoryName"
    }

    // --- Admin Screens ---
    data object AdminMain : Screen("admin_main_screen")
    data object AdminDashboard : Screen("admin_dashboard_screen")
    data object AdminManageBooks : Screen("admin_manage_books_screen")
    data object AdminManageUsers : Screen("admin_manage_users_screen")
    data object AdminManageCategory : Screen("admin_manage_category_screen")
}