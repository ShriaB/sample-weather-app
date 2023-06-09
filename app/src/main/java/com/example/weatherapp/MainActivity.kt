package com.example.weatherapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.domain.model.weather.HourlyWeatherData
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.services.LocationUpdateService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.permissionx.guolindev.PermissionX
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<WeatherViewModel>()

    // Provides the device location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationService: LocationUpdateService? = null
    private var bound: Boolean = false


    val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
            locationService = (binder as LocationUpdateService.LocalBinder).getService()
            bound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            locationService = null
            bound = false
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Mydebug", "In OnCreate of activity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Requesting location permission
         */
        requestLocationPermission()

        /**
         * Observing the WeatherDataState in the viewModel
         * If it has finished loading and the data is not null then data is fetched successfully
         * Render the data to the UI
         */
        viewModel.weatherDataState.observe(this){
            if(!it.isLoading){
                it.data?.let{weatherData ->
                    binding.currentWeatherData = weatherData.currentWeatherData
                    binding.lifecycleOwner = this
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Mydebug", "In OnStart of activity")
    }

    override fun onStop() {
        Log.d("Mydebug", "In OnStop of activity")
        if(bound){
            unbindService(serviceConnection)
            bound = false
        }
        super.onStop()
    }

    /**
     * Requesting for access to device location using PermissionX
     * If granted then calling the load data function
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestLocationPermission(){
        Log.d("Mydebug", "In requestLocationPermission of activity")
        PermissionX.init(this)
            .permissions(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.FOREGROUND_SERVICE, android.Manifest.permission.POST_NOTIFICATIONS)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                    Intent(this, LocationUpdateService::class.java).also { intent ->
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        {
                            // Required for Oreo and later
                            startForegroundService(intent)
                        }
                        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE )
                    }
                    locationService?.let {
                        it.getLastLocation()
                        Log.d("Mydebug", "${it.location.latitude}, ${it.location.longitude}, ${it.time}")
                    }
                } else {
                    Toast.makeText(this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
                }
            }
    }

    /**
     * Getting the current location of the device
     * Passing the latitude and longitude to fetch the weather data of that location
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("MissingPermission")
    fun loadData(){
        // Getting the last Location and calling the api
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                location?.let {
                    viewModel.getWeatherData(it.latitude, it.longitude)
                }
            }
    }
}
