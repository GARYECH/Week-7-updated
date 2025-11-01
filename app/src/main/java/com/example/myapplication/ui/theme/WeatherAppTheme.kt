package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Typography
import androidx.compose.material3.Shapes

private val LightColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF0E2A44),
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC5)
)

@Composable
fun WeatherAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
