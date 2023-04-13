package com.example.weatherapp.common

/**
 * @property data Holds the data fetched from API
 * @property error Stores the error message in case of any error
 */
sealed class Result<T>(val data: T? = null, val error: String? = null){
    class Loading<T>: Result<T>()
    class Success<T>(data: T): Result<T>(data)
    class Error<T>(data: T?=null, message: String? = null): Result<T>(data, message)
}
