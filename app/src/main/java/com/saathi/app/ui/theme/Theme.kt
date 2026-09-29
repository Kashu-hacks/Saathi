package com.saathi.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SaathiLightColors = lightColorScheme(
    primary = DeepPlum,
    onPrimary = Color.White,
    primaryContainer = SoftPink,
    onPrimaryContainer = TextDark,
    secondaryContainer = CalmLavender,
    onSecondaryContainer = TextDark,
    tertiary = SageGreen,
    background = WarmBeige,
    onBackground = TextDark,
    surface = WarmBeige,
    onSurface = TextDark,
    error = ErrorRed
)

@Composable
fun SaathiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SaathiLightColors,
        typography = SaathiTypography,
        content = content
    )
}
