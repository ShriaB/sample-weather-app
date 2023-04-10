package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.remote.dto.WeatherDto

interface WeatherRepository {
    suspend fun getWeather(lat: Double, long: Double): WeatherDto
}