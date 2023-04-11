package com.example.weatherapp.domain.model.weather

data class WeatherDataState(
    val isLoading: Boolean = false,
    val data: WeatherData? = null,
    val error: String? = null
)