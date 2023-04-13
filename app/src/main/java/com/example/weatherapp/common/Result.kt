package com.example.weatherapp.common


sealed class Result<T>(val data: T? = null, val error: String? = null){
    class Loading<T>: Result<T>()
    class Success<T>(data: T): Result<T>(data)
    class Error<T>(data: T?=null, message: String? = null): Result<T>(data, message)
}
