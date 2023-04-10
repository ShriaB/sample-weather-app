package com.example.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class Hourly(@Json(name = "pressure_msl")
                  val pressure: List<Double>?,
                  @Json(name = "temperature_2m")
                  val temperature: List<Double>?,
                  @Json(name = "relativehumidity_2m")
                  val humidity: List<Int>?,
                  @Json(name = "weathercode")
                  val weatherCode: List<Int>?,
                  @Json(name = "windspeed_10m")
                  val windSpeed: List<Double>?,
                  @Json(name = "time")
                  val time: List<String>?)