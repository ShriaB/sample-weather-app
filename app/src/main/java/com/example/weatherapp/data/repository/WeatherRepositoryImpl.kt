package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.remote.WeatherApiService
import com.example.weatherapp.data.remote.dto.WeatherDto
import com.example.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi : WeatherApiService
): WeatherRepository {

    /**
     * Takes the latitude and longitude
     * Returns the data returned from weatherApi
     */
    override suspend fun getWeather(lat: Double, long: Double): WeatherDto {
        return weatherApi.getWeather(lat, long)
    }
}