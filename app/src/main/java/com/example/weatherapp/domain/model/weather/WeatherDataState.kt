package com.example.weatherapp.domain.model.weather

/**
 * @property isLoading if data is loading or not
 * @property data holds an object of WeatherData containing hourly weather data for a week
 * @property error holds the error message if any
 */
data class WeatherDataState(
    val isLoading: Boolean = false,
    val data: WeatherData? = null,
    val error: String? = null
)