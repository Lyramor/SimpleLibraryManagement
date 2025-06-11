package com.example.simplelibrarymanagement.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = Color(0xFF4A2C2A),
    onPrimaryContainer = Color(0xFFFFDAD4),

    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = Color(0xFF3D4A52),
    onSecondaryContainer = Color(0xFFD1E4ED),

    tertiary = CategoryFiction,
    onTertiary = OnPrimary,
    tertiaryContainer = Color(0xFF4A2A4A),
    onTertiaryContainer = Color(0xFFE1BEE7),

    error = Error,
    onError = OnPrimary,
    errorContainer = Color(0xFF5F2120),
    onErrorContainer = Color(0xFFFFDAD6),

    background = Color(0xFF121212),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1D1B20),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),

    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFF49454F),
    scrim = Color(0xFF000000),

    inverseSurface = Color(0xFFE6E1E5),
    inverseOnSurface = Color(0xFF322F35),
    inversePrimary = Primary,

    surfaceDim = Color(0xFF141218),
    surfaceBright = Color(0xFF3B383E),
    surfaceContainerLowest = Color(0xFF0F0D13),
    surfaceContainerLow = Color(0xFF1D1B20),
    surfaceContainer = Color(0xFF211F26),
    surfaceContainerHigh = Color(0xFF2B2930),
    surfaceContainerHighest = Color(0xFF36343B)
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = TextPrimary,

    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = TextPrimary,

    tertiary = CategoryFiction,
    onTertiary = OnPrimary,
    tertiaryContainer = Color(0xFFE1BEE7),
    onTertiaryContainer = Color(0xFF31111D),

    error = Error,
    onError = OnPrimary,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFFBA1A1A),

    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    outline = Border,
    outlineVariant = Divider,
    scrim = Shadow,

    inverseSurface = Color(0xFF322F35),
    inverseOnSurface = Color(0xFFF4EFF4),
    inversePrimary = Color(0xFFFFB4AB),

    surfaceDim = Color(0xFFDDD8DD),
    surfaceBright = Background,
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF7F2F7),
    surfaceContainer = Surface,
    surfaceContainerHigh = Color(0xFFF1ECF1),
    surfaceContainerHighest = Color(0xFFEBE6EB)
)

@Composable
fun SimpleLibraryManagementTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Disabled for consistency
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LibraryTypography,
        shapes = LibraryShapes,
        content = content
    )
}

// Enhanced theme extensions
val MaterialTheme.libraryColors: LibraryColors
    @Composable
    get() = LibraryColorsProvider.current

val MaterialTheme.libraryShapes: CustomShapes
    @Composable
    get() = CustomShapes

// Typography extension
val MaterialTheme.libraryTypography: CustomTypography
    @Composable
    get() = CustomTypography

// Provider object for custom colors
object LibraryColorsProvider {
    val current: LibraryColors
        @Composable
        get() = LibraryColors(
            available = Available,
            availableContainer = Color(0xFFD8F3D9), // Example color
            borrowed = Borrowed,
            borrowedContainer = Color(0xFFFFECB3), // Example color
            overdue = Overdue,
            overdueContainer = Color(0xFFFFCDD2), // Example color
            categoryFiction = CategoryFiction,
            categoryNonFiction = CategoryNonFiction,
            categoryScience = CategoryScience,
            categoryHistory = CategoryHistory,
            categoryBiography = CategoryBiography,
            categoryTechnology = Color(0xFF2196F3), // Example color
            categoryArt = Color(0xFF795548), // Example color
            categoryPhilosophy = Color(0xFF607D8B), // Example color
            success = Success,
            successContainer = Color(0xFFD8F3D9), // Example color
            warning = Warning,
            warningContainer = Color(0xFFFFF3CD), // Example color
            info = Info,
            infoContainer = Color(0xFFD1ECF1), // Example color
            inputBackground = InputBackground,
            inputBorder = InputBorder,
            inputFocused = InputFocused
        )
}

// Enhanced data class for custom colors
data class LibraryColors(
    val available: Color,
    val availableContainer: Color,
    val borrowed: Color,
    val borrowedContainer: Color,
    val overdue: Color,
    val overdueContainer: Color,
    val categoryFiction: Color,
    val categoryNonFiction: Color,
    val categoryScience: Color,
    val categoryHistory: Color,
    val categoryBiography: Color,
    val categoryTechnology: Color,
    val categoryArt: Color,
    val categoryPhilosophy: Color,
    val success: Color,
    val successContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val info: Color,
    val infoContainer: Color,
    val inputBackground: Color,
    val inputBorder: Color,
    val inputFocused: Color
)