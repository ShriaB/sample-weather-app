package com.example.weatherapp

import android.content.res.Resources
import android.media.Image
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime

@BindingAdapter("setImage")
fun setImage(view: ImageView, resId: Int){
    view.setImageResource(resId)
}

@BindingAdapter("tempText")
fun setTemperatureText(view: TextView, temp: Double){
    view.text = "$temp℃"
}

@BindingAdapter("pressureText")
fun setPressureText(view: TextView, pressure: Double){
    view.text = "${pressure}hPa"
}

@BindingAdapter("humidityText")
fun setHumidityText(view: TextView, humidity: Int){
    view.text = "${humidity}%"
}

@BindingAdapter("windText")
fun setWindText(view: TextView, wind: Double){
    view.text = "${wind}km/h"
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("today")
fun setTodayText(view: TextView, time: LocalDateTime?){
    time?.let {
        view.text = "Today: ${time.hour}:00"
    }
}
