package com.example.myapplication

import android.R.attr.apiKey
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.example.myapplication.navigation.AppNav
import com.example.myapplication.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val apiKey = "0802a34d407f2563eb4ee1eff4e30a96"

        setContent {
            WeatherAppTheme {
                AppNav(apiKey = apiKey)
            }
        }
    }
}
