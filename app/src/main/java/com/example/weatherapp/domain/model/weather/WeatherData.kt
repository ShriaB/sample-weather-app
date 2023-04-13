package com.example.weatherapp.domain.model.weather

/**
 * @property dailyWeatherData Maps the weather data of a day with an index eq: 0 -> Weather data of today, 1-> Data of tomorrow etc.
 * @property currentWeatherData Stores the data of the current hour
 */
data class WeatherData(
    val dailyWeatherData: Map<Int, List<HourlyWeatherData>>,
    val currentWeatherData : HourlyWeatherData
)
