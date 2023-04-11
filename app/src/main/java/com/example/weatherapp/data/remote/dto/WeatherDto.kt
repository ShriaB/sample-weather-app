package com.example.weatherapp.data.remote.dto

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherapp.domain.model.weather.HourlyWeatherData
import com.example.weatherapp.domain.model.weather.WeatherData
import com.example.weatherapp.domain.model.weather.WeatherType.Companion.fromWMO
import com.squareup.moshi.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class WeatherDto(@Json(name = "elevation")
                      val elevation: Int = 0,
                      @Json(name = "hourly_units")
                      val hourlyUnits: HourlyUnits,
                      @Json(name = "generationtime_ms")
                      val generationtimeMs: Double = 0.0,
                      @Json(name = "timezone_abbreviation")
                      val timezoneAbbreviation: String = "",
                      @Json(name = "timezone")
                      val timezone: String = "",
                      @Json(name = "latitude")
                      val latitude: Double = 0.0,
                      @Json(name = "utc_offset_seconds")
                      val utcOffsetSeconds: Int = 0,
                      @Json(name = "hourly")
                      val hourly: Hourly,
                      @Json(name = "longitude")
                      val longitude: Double = 0.0)


/**
 * MAPPER FUNCTIONS
 */
@RequiresApi(Build.VERSION_CODES.O)
private fun WeatherDto.toDailyWeatherDataMap(): Map<Int, List<HourlyWeatherData>> {
    val currentDayOfMonth = LocalDateTime.now().dayOfMonth
    val dailyWeatherDataMap = hourly.time?.mapIndexed { index, time ->
        HourlyWeatherData(
            time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
            temperature = hourly.temperature?.get(index) ?: 0.0,
            pressure = hourly.pressure?.get(index) ?: 0.0,
            humidity = hourly.humidity?.get(index) ?: 0,
            windSpeed = hourly.windSpeed?.get(index) ?: 0.0,
            weatherType = fromWMO(hourly.weatherCode?.get(index) ?: 0)
        )
    }?.groupBy(keySelector = {it.time.dayOfMonth - currentDayOfMonth})
        .also {
            Log.d("Mydebug", "$it") }

    return dailyWeatherDataMap!!
}

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherDto.toWeatherData(): WeatherData {
    val now = LocalDateTime.now()
    val dailyWeatherData = toDailyWeatherDataMap()
    val currentWeatherData = dailyWeatherData[0]?.find{
        val hour = if (now.minute < 30)  now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherData(
        dailyWeatherData = dailyWeatherData,
        currentWeatherData = currentWeatherData!!
    )
}