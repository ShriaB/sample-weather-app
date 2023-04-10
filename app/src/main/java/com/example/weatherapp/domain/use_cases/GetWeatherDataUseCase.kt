package com.example.weatherapp.domain.use_cases

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.Resource
import com.example.weatherapp.data.remote.dto.toWeatherData
import com.example.weatherapp.domain.model.WeatherData
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetWeatherDataUseCase(
    private val repository: WeatherRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(lat: Double, long: Double) = flow{
        emit(Resource.Loading())
        val weatherData = repository.getWeather(lat, long).toWeatherData()
        emit(Resource.Success(weatherData))
    }.catch {
        emit(Resource.Error(null, it.message))
    }
}