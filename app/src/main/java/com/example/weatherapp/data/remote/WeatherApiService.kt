package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    /**
     * Takes the latitude and longitude of users location
     * Returns the data fetched from API
     */
    @GET("/v1/forecast?hourly=temperature_2m,relativehumidity_2m,pressure_msl,windspeed_10m,weathercode")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto
}