package com.example.weatherapp.domain.model.weather

data class WeatherData(
    val dailyWeatherData: Map<Int, List<HourlyWeatherData>>,
    val currentWeatherData : HourlyWeatherData
)
