package com.example.demosample.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(

    // Backgrounds
    val background: Color,
    val surface: Color,
    val container: Color,

    // Text
    val textPrimary: Color,
    val textSecondary: Color,
    val selectedText: Color,
    val unSelectedText: Color,

    // Actions
    val primary: Color,
    val onPrimary: Color,

    // Error
    val error: Color
)