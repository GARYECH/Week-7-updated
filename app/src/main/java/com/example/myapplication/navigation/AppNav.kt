package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import com.example.myapplication.ui.screen.WeatherApp

@Composable
fun AppNav(apiKey: String) {
    WeatherApp(apiKey = apiKey)
}