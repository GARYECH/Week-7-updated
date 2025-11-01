package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.WeatherResponse
import com.example.myapplication.data.service.WeatherService

class WeatherRepository(private val api: WeatherService) {
    suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
        return api.getWeather(city, apiKey)
    }
}
