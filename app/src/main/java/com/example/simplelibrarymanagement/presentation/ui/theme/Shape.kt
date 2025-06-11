package com.example.simplelibrarymanagement.presentation.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val LibraryShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(18.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

object CustomShapes {
    // Button shapes - More modern rounded corners
    val ButtonSmall = RoundedCornerShape(8.dp)
    val ButtonMedium = RoundedCornerShape(12.dp)
    val ButtonLarge = RoundedCornerShape(16.dp)
    val ButtonRounded = RoundedCornerShape(50.dp)

    // Card shapes - Enhanced elevation appearance
    val CardSmall = RoundedCornerShape(12.dp)
    val CardMedium = RoundedCornerShape(16.dp)
    val CardLarge = RoundedCornerShape(20.dp)
    val CardElevated = RoundedCornerShape(18.dp)

    // Input field shapes
    val InputField = RoundedCornerShape(12.dp)
    val SearchField = RoundedCornerShape(28.dp)

    // Book cover shape
    val BookCover = RoundedCornerShape(8.dp)
    val BookCoverLarge = RoundedCornerShape(12.dp)

    // Avatar shapes
    val AvatarSmall = RoundedCornerShape(50.dp)
    val AvatarMedium = RoundedCornerShape(50.dp)
    val AvatarSquare = RoundedCornerShape(12.dp)

    // Dialog and sheet shapes
    val Dialog = RoundedCornerShape(20.dp)
    val BottomSheet = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 24.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )

    // Status and chip shapes
    val StatusChip = RoundedCornerShape(8.dp)
    val CategoryChip = RoundedCornerShape(6.dp)
    val Tab = RoundedCornerShape(24.dp)
    val TabIndicator = RoundedCornerShape(16.dp)

    // Progress and notification
    val ProgressIndicator = RoundedCornerShape(6.dp)
    val NotificationCard = RoundedCornerShape(16.dp)
    val Badge = RoundedCornerShape(50.dp)

    // Floating Action Button
    val Fab = RoundedCornerShape(16.dp)
    val FabLarge = RoundedCornerShape(20.dp)
}