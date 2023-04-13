package com.example.weatherapp.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Result
import com.example.weatherapp.domain.model.weather.WeatherDataState
import com.example.weatherapp.domain.use_cases.GetWeatherDataUseCase
import kotlinx.coroutines.launch

/**
 * @property _weatherDataState Stores the status of the API call
 *  - Loading: isLoading = true
 *  - Successful - data = WeatherData
 *  - Error - error = Error message
 */

@RequiresApi(Build.VERSION_CODES.O)
class WeatherViewModel(
    private val useCase: GetWeatherDataUseCase
): ViewModel() {

    private val _weatherDataState = MutableLiveData<WeatherDataState>()
    val weatherDataState: LiveData<WeatherDataState>
        get() = _weatherDataState

    /**
     * Calling the UseCase GetWeatherDataUseCase by passing the latitude and longitude
     * Collecting the values emitted by the flow
     * Accordingly changing the status of the _weatherDataState
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeatherData(lat: Double, long: Double){
        Log.d("Mydebug", "$lat, $long")
        viewModelScope.launch {
            useCase(lat, long).collect{ res->
                when (res){
                    is Result.Loading ->{
                        _weatherDataState.value = WeatherDataState(isLoading = true)
                    }
                    is Result.Success -> {
                        _weatherDataState.value = WeatherDataState(data = res.data)
                    }
                    else -> {
                        _weatherDataState.value = WeatherDataState(error = res.error)
                    }
                }
            }
        }
    }
}