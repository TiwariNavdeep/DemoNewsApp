package com.example.demosample.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkAppColors = AppColors(

    // Backgrounds
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    container = Color(0xFF252525),

    // Text
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFB0B0B0),
    selectedText = Color(0xFF2979FF),
    unSelectedText = Color(0xFF7A7A7A),

    // Primary
    primary = Color(0xFF2979FF),
    onPrimary = Color(0xFF000000),

    // Error
    error = Color(0xFFEF5350)
)
private val LightAppColors = AppColors(

    // Backgrounds
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF8F9FA),
    container = Color(0xFFF1F3F4),

    // Text
    textPrimary = Color(0xFF1B1A1E),
    textSecondary = Color(0xFF6B6B6B),
    selectedText = Color(0xFF1565C0),
    unSelectedText = Color(0xFF9E9E9E),

    // Primary
    primary = Color(0xFF1565C0),
    onPrimary = Color(0xFFFFFFFF),

    // Error
    error = Color(0xFFD32F2F)
)

val LocalAppColors = compositionLocalOf<AppColors> {
    error("AppColors not provided")
}

@Composable
fun MyApplicationDemoTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkAppColors else LightAppColors
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = colors.background,
            darkIcons = !darkTheme
        )
    }
    CompositionLocalProvider(
        LocalAppColors provides colors
    ) {
        MaterialTheme(
            typography = AppTypography.Typography,
            content = content
        )
    }

}