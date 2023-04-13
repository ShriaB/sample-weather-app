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

    /**
     * Takes the latitude and longitude
     * Returns a flow
     * emits the data fetched from the repository if API call successful
     * else emits error
     */
    @RequiresApi(Build.VERSION_CODES.O)
    // We override the invoke operator so that we can call this function just by using the object
    // No function name required as ideally UseCases perform a single task.
    operator fun invoke(lat: Double, long: Double) : Flow<Result<WeatherData>> = flow{
        emit(Result.Loading())
        val weatherData = repository.getWeather(lat, long).toWeatherData()
        emit(Result.Success(weatherData))
    }.catch {
        Log.d("Mydebug", "Error in usecase: ${it.message}")
        emit(Result.Error(null, it.message))
    }
}