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
    primaryContainer = PrimaryDark,
    onPrimaryContainer = OnPrimary,

    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = OnSecondary,

    tertiary = CategoryFiction,
    onTertiary = OnPrimary,
    tertiaryContainer = CategoryScience,
    onTertiaryContainer = OnPrimary,

    error = Error,
    onError = OnPrimary,
    errorContainer = PrimaryDark,
    onErrorContainer = OnPrimary,

    background = Color(0xFF121212),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFE0E0E0),
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFB0B0B0),

    outline = Color(0xFF404040),
    outlineVariant = Color(0xFF2C2C2C),
    scrim = Color(0xFF000000),

    inverseSurface = Color(0xFFE0E0E0),
    inverseOnSurface = Color(0xFF1E1E1E),
    inversePrimary = PrimaryDark,

    surfaceDim = Color(0xFF181818),
    surfaceBright = Color(0xFF2C2C2C),
    surfaceContainerLowest = Color(0xFF0F0F0F),
    surfaceContainerLow = Color(0xFF1A1A1A),
    surfaceContainer = Color(0xFF1E1E1E),
    surfaceContainerHigh = Color(0xFF282828),
    surfaceContainerHighest = Color(0xFF323232)
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
    onTertiaryContainer = TextPrimary,

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

    inverseSurface = Color(0xFF313030),
    inverseOnSurface = Color(0xFFF4F0F0),
    inversePrimary = PrimaryLight,

    surfaceDim = Color(0xFFE8E0E0),
    surfaceBright = Color(0xFFFFFBFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFEF7F7),
    surfaceContainer = Surface,
    surfaceContainerHigh = Color(0xFFF3EEEE),
    surfaceContainerHighest = Color(0xFFEDE9E9)
)

@Composable
fun SimpleLibraryManagementTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LibraryTypography,
        shapes = LibraryShapes,
        content = content
    )
}

// Theme extensions for easier access to custom colors
val MaterialTheme.libraryColors: LibraryColors
    @Composable
    get() = LibraryColorsProvider.current

// Theme extensions for custom shapes
val MaterialTheme.libraryShapes: CustomShapes
    @Composable
    get() = CustomShapes

// Theme extensions for custom typography
val MaterialTheme.libraryTypography: CustomTypography
    @Composable
    get() = CustomTypography

// Provider object to access custom theme colors
object LibraryColorsProvider {
    val current: LibraryColors
        @Composable
        get() = LibraryColors(
            available = Available,
            borrowed = Borrowed,
            overdue = Overdue,
            categoryFiction = CategoryFiction,
            categoryNonFiction = CategoryNonFiction,
            categoryScience = CategoryScience,
            categoryHistory = CategoryHistory,
            categoryBiography = CategoryBiography,
            success = Success,
            warning = Warning,
            info = Info,
            inputBackground = InputBackground,
            inputBorder = InputBorder,
            inputFocused = InputFocused
        )
}

// Data class for custom colors
data class LibraryColors(
    val available: Color,
    val borrowed: Color,
    val overdue: Color,
    val categoryFiction: Color,
    val categoryNonFiction: Color,
    val categoryScience: Color,
    val categoryHistory: Color,
    val categoryBiography: Color,
    val success: Color,
    val warning: Color,
    val info: Color,
    val inputBackground: Color,
    val inputBorder: Color,
    val inputFocused: Color
)
