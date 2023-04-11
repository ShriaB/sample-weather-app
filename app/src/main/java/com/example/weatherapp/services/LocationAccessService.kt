package com.example.weatherapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.android.gms.location.FusedLocationProviderClient

class LocationAccessService(): Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}