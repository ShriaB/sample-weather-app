package com.example.weatherapp

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication: Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        /**
         * Instantiation of Koin for dependency injection
         */
        startKoin {
            androidContext(this@WeatherApplication)
            modules(appModule)
        }
    }
}