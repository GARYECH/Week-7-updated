package com.example.myapplication.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("dt") val dt: Long?,
    @SerializedName("sys") val sys: Sys?,
    @SerializedName("weather") val weather: List<WeatherItem>?,
    @SerializedName("main") val main: Main?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("clouds") val clouds: Clouds?,
    @SerializedName("rain") val rain: Rain?
)

data class WeatherItem(
    @SerializedName("main") val main: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?
)

data class Main(
    @SerializedName("temp") val temp: Double?,
    @SerializedName("feels_like") val feelsLike: Double?,
    @SerializedName("pressure") val pressure: Int?,
    @SerializedName("humidity") val humidity: Int?
)

data class Wind(
    @SerializedName("speed") val speed: Double?
)

data class Sys(
    @SerializedName("sunrise") val sunrise: Long?,
    @SerializedName("sunset") val sunset: Long?
)

data class Clouds(
    @SerializedName("all") val all: Int?
)

data class Rain(
    @SerializedName("1h") val oneHour: Double?,
    @SerializedName("3h") val threeHours: Double?
)