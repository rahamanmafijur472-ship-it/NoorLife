package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BrightGold,
    onPrimary = EmeraldDark,
    primaryContainer = EmeraldPrimary,
    onPrimaryContainer = LightGold,
    secondary = EmeraldLight,
    onSecondary = Color.White,
    secondaryContainer = CharcoalSurfaceVariant,
    onSecondaryContainer = EmeraldContainer,
    tertiary = WarmGold,
    onTertiary = CharcoalDark,
    background = CharcoalDark,
    onBackground = TextPrimaryDark,
    surface = CharcoalSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = CharcoalSurfaceVariant,
    onSurfaceVariant = TextSecondaryDark,
    outline = CharcoalBorder
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimary,
    onPrimary = Color.White,
    primaryContainer = EmeraldContainer,
    onPrimaryContainer = OnEmeraldContainer,
    secondary = WarmGold,
    onSecondary = CharcoalDark,
    secondaryContainer = GoldContainer,
    onSecondaryContainer = OnGoldContainer,
    tertiary = TealAccent,
    onTertiary = Color.White,
    background = IvoryBackground,
    onBackground = TextPrimaryLight,
    surface = IvorySurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = IvorySurfaceVariant,
    onSurfaceVariant = TextSecondaryLight,
    outline = Color(0xFFD8D2C4)
)

@Composable
fun NoorLifeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
