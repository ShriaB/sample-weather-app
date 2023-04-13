package com.example.weatherapp.domain.model.weather

import java.time.LocalDateTime

/**
 * @property time Stores the date time; Time consists of the hour
 * @property temperature Temperature of that hour in that day in ℃
 * @property pressure Atmospheric Pressure in hPa
 * @property humidity Humidity in %
 * @property windSpeed Speed of the wind in km/h
 * @property weatherType Description of the weather
 */
data class HourlyWeatherData (
        val time: LocalDateTime,
        val temperature: Double,
        val pressure: Double,
        val humidity: Int,
        val windSpeed: Double,
        val weatherType: WeatherType,
)
