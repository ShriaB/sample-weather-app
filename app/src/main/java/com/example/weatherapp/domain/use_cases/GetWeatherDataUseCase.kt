package com.example.weatherapp.domain.use_cases

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherapp.common.Result
import com.example.weatherapp.data.remote.dto.toWeatherData
import com.example.weatherapp.domain.model.weather.WeatherData
import com.example.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetWeatherDataUseCase(
    private val repository: WeatherRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(lat: Double, long: Double) : Flow<Result<WeatherData>> = flow{
        emit(Result.Loading())
        val weatherData = repository.getWeather(lat, long).toWeatherData()
        emit(Result.Success(weatherData))
    }.catch {
        Log.d("Mydebug", "Error in usecase: ${it.message}")
        emit(Result.Error(null, it.message))
    }
}