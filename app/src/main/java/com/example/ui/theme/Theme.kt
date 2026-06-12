package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

private val VibrantColorScheme = lightColorScheme(
    primary = Color(0xFF4F46E5),        // Indigo 600
    onPrimary = Color.White,
    secondary = Color(0xFFF59E0B),      // Amber 500 (representing coins/XP metric)
    onSecondary = Color.Black,
    tertiary = Color(0xFF10B981),       // Emerald 500
    onTertiary = Color.White,
    background = Color(0xFFF8FAFC),     // Slate 50
    onBackground = Color(0xFF0F172A),   // Slate 900
    surface = Color.White,
    onSurface = Color(0xFF0F172A),      // Slate 900
    surfaceVariant = Color(0xFFE2E8F0), // Slate 200
    onSurfaceVariant = Color(0xFF64748B), // Slate 500
    outline = Color(0xFFE2E8F0),        // Slate 200 border
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Always show the glorious Vibrant Palette
  content: @Composable () -> Unit,
) {
  val colorScheme = VibrantColorScheme
  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
