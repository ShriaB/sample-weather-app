package com.example.weatherapp.data.remote.dto

import com.squareup.moshi.Json

data class HourlyUnits(@Json(name = "pressure_msl")
                       val pressureMsl: String = "",
                       @Json(name = "temperature_2m")
                       val temperatureM: String = "",
                       @Json(name = "relativehumidity_2m")
                       val relativehumidityM: String = "",
                       @Json(name = "weathercode")
                       val weathercode: String = "",
                       @Json(name = "windspeed_10m")
                       val windspeedM: String = "",
                       @Json(name = "time")
                       val time: String = "")