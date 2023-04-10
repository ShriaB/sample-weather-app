package com.example.weatherapp.domain.model

data class WeatherData(
    val dailyWeatherData: Map<Int, List<HourlyWeatherData>>,
    val currentWeatherData : HourlyWeatherData
)
