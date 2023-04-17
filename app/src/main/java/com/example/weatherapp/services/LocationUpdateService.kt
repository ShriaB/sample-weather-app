package com.example.weatherapp.services

import android.app.*
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.weatherapp.MainActivity
import com.google.android.gms.location.*
import java.time.LocalDateTime


class LocationUpdateService(): Service() {

    private val binder = LocalBinder()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    var location = Location("locationProvider")
    @RequiresApi(Build.VERSION_CODES.O)
    var time = LocalDateTime.now()

    private val channelId = "com.weatherapp"
    private val notificationId = 1234
    private lateinit var notificationManager: NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        Log.d("Mydebug", "In Oncreate of service")

        location.latitude = 0.0
        location.longitude = 0.0

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let{
                        location.latitude = it.latitude
                        location.longitude = it.longitude
                        time = LocalDateTime.now()
                    Log.d("Service", "lat:${it.latitude}, long:${it.longitude}, $time")
                }
            }
        }
        createLocationRequest()
        getLastLocation()
        requestLocationUpdates()
        createNotificationChannel()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d("Mydebug", "In onStartCommand of service")
        return START_NOT_STICKY
    }

    inner class LocalBinder: Binder() {
        fun getService() : LocationUpdateService = this@LocationUpdateService
    }
    override fun onBind(p0: Intent?): IBinder? {
        /**
         * We want to interact with this service,
         * Request and receive data into our Activity
         * Hence we bind it to the activity
         */
        Log.d("Mydebug", "In onBind of service")
        stopForeground(STOP_FOREGROUND_DETACH)
        return binder
    }

    override fun onRebind(intent: Intent?) {
        Log.d("Mydebug", "In onReBind of service")
        stopForeground(STOP_FOREGROUND_DETACH)
        super.onRebind(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("Mydebug", "In onUnBind of service")
        startForeground(notificationId, getNotification())
        return true
    }

    private fun createLocationRequest(){
        Log.d("Mydebug", "In createLocationRequest of service")
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            10000
        ).build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("MissingPermission")
    fun getLastLocation(){
        Log.d("Mydebug", "In getLastLocation of service")
        fusedLocationClient.lastLocation
            .addOnSuccessListener { locationRes : Location? ->
                // Got last known location. In some rare situations this can be null.
                locationRes?.let {
                    location.latitude = it.latitude
                    location.longitude = it.longitude
                    time = LocalDateTime.now()
                }
                Log.d("Service", "$locationRes, $time")

            }
    }
    @Suppress("MissingPermission")
    fun requestLocationUpdates(){
        Log.d("Mydebug", "In requestLocationUpdates of service")
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    private fun createNotificationChannel(){
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            Log.d("Mydebug", "In createNotificationChannel of service")
            val name = "Location and Time"
            val descriptionText = "Displays current Location and curent time"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(channelId, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotification(): Notification{
        Log.d("Mydebug", "In getNotification of service")
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this,channelId)
            .setSmallIcon(com.example.weatherapp.R.drawable.baseline_notifications_24)
            .setContentTitle("Location and time")
            .setContentText("${location.latitude}, ${location.longitude}, $time")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setOngoing(true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            builder.setChannelId(channelId)
        }
        return builder.build()
    }
}