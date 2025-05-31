package com.example.simplelibrarymanagement.presentation.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val LibraryShapes = Shapes(
    // Extra Small - for small elements like chips, badges
    extraSmall = RoundedCornerShape(4.dp),

    // Small - for buttons, input fields
    small = RoundedCornerShape(8.dp),

    // Medium - for cards, containers
    medium = RoundedCornerShape(12.dp),

    // Large - for sheets, large containers
    large = RoundedCornerShape(16.dp),

    // Extra Large - for full screen elements
    extraLarge = RoundedCornerShape(20.dp)
)

// Custom shapes for specific components
object CustomShapes {
    // Button shapes
    val ButtonSmall = RoundedCornerShape(6.dp)
    val ButtonMedium = RoundedCornerShape(8.dp)
    val ButtonLarge = RoundedCornerShape(12.dp)
    val ButtonRounded = RoundedCornerShape(50.dp)

    // Card shapes
    val CardSmall = RoundedCornerShape(8.dp)
    val CardMedium = RoundedCornerShape(12.dp)
    val CardLarge = RoundedCornerShape(16.dp)

    // Input field shapes
    val InputField = RoundedCornerShape(8.dp)
    val SearchField = RoundedCornerShape(24.dp)

    // Book cover shape (slightly rounded rectangle)
    val BookCover = RoundedCornerShape(6.dp)

    // Avatar shapes
    val AvatarSmall = RoundedCornerShape(50.dp)
    val AvatarMedium = RoundedCornerShape(50.dp)
    val AvatarSquare = RoundedCornerShape(8.dp)

    // Dialog and sheet shapes
    val Dialog = RoundedCornerShape(16.dp)
    val BottomSheet = RoundedCornerShape(
        topStart = 20.dp,
        topEnd = 20.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    // Tab shapes
    val Tab = RoundedCornerShape(20.dp)
    val TabIndicator = RoundedCornerShape(12.dp)

    // Progress indicator
    val ProgressIndicator = RoundedCornerShape(4.dp)

    // Notification shapes
    val NotificationCard = RoundedCornerShape(12.dp)
    val Badge = RoundedCornerShape(50.dp)
}