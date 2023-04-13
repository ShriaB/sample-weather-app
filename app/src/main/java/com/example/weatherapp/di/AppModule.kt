package com.example.weatherapp.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.data.remote.WeatherApiService
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.use_cases.GetWeatherDataUseCase
import com.example.weatherapp.presentation.WeatherViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RequiresApi(Build.VERSION_CODES.O)
val appModule = module{

    /**
     * Retrofit REST Client
     * With Moshi converter
     */
    single{
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com")
            .addConverterFactory(MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(WeatherApiService::class.java)
    }

    single<WeatherRepository>{
        WeatherRepositoryImpl(get())
    }

    single{
        GetWeatherDataUseCase(get())
    }

    viewModel {
        WeatherViewModel(get())
    }
}