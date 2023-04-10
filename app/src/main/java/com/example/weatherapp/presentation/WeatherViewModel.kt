package com.example.weatherapp.presentation

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.use_cases.GetWeatherDataUseCase

class WeatherViewModel(
    private val useCase: GetWeatherDataUseCase
): ViewModel() {

}