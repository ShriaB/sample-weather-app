package com.example.weatherapp


sealed class Resource<T>(val data: T? = null, val error: String? = null){
    class Loading<T>: Resource<T>()
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(data: T?=null, message: String? = null): Resource<T>(data, message)
}
