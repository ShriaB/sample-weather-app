package com.example.weatherapp.domain.model

import java.time.LocalDateTime

data class HourlyWeatherData (
        val time: LocalDateTime,
        val temperature: Double,
        val pressure: Double,
        val humidity: Int,
        val windSpeed: Double,
        val weatherType: WeatherType,
)
