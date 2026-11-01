package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = DeepSkyBlue,
    onPrimary = Color.White,
    primaryContainer = SkyBlue.copy(alpha = 0.25f),
    onPrimaryContainer = DarkNavy,
    secondary = Tangerine,
    onSecondary = Color.White,
    secondaryContainer = SunnyYellow.copy(alpha = 0.3f),
    onSecondaryContainer = DarkNavy,
    tertiary = ForestMint,
    onTertiary = Color.White,
    tertiaryContainer = MintGreen.copy(alpha = 0.3f),
    onTertiaryContainer = DarkNavy,
    background = LightBg,
    onBackground = DarkNavy,
    surface = CardBg,
    onSurface = DarkNavy,
    surfaceVariant = Color(0xFFEDF2F9),
    onSurfaceVariant = SoftNavy,
    outline = Color(0xFFE2E8F0)
)

private val DarkColorScheme = darkColorScheme(
    primary = SkyBlue,
    onPrimary = DarkNavy,
    primaryContainer = DeepSkyBlue.copy(alpha = 0.4f),
    onPrimaryContainer = Color.White,
    secondary = SunnyYellow,
    onSecondary = DarkNavy,
    tertiary = MintGreen,
    onTertiary = DarkNavy,
    background = Color(0xFF161F2E),
    onBackground = Color(0xFFF0F4F8),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF0F4F8),
    surfaceVariant = Color(0xFF28354A),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF334155)
)

val KidsShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(26.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun KidsWorldTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = KidsShapes,
        content = content
    )
}

// Keep backwards-compatible alias
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    KidsWorldTheme(darkTheme = darkTheme, content = content)
}
