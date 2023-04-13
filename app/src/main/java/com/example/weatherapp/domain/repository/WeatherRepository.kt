package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.remote.dto.WeatherDto

/**
 * Provides a common interface for fetching the data from the data source
 */
interface WeatherRepository {
    suspend fun getWeather(lat: Double, long: Double): WeatherDto
}