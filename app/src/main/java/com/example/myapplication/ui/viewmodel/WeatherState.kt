package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.container.Container
import com.example.myapplication.data.dto.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

sealed class WeatherState {
    object Idle : WeatherState()
    object Loading : WeatherState()
    data class Success(val data: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
}

class WeatherViewModel : ViewModel() {
    private val repo = Container.repository

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val state: StateFlow<WeatherState> = _state

    fun getWeather(city: String, apiKey: String) {
        if (city.isBlank()) {
            _state.value = WeatherState.Error("Please enter a city name")
            return
        }
        viewModelScope.launch {
            _state.value = WeatherState.Loading
            try {
                val res = repo.getWeather(city.trim(), apiKey)
                _state.value = WeatherState.Success(res)
            } catch (t: Throwable) {
                val msg = when (t) {
                    is UnknownHostException -> "No internet connection"
                    else -> (t.message ?: "Unknown error")
                }
                _state.value = WeatherState.Error(msg)
            }
        }
    }
}
