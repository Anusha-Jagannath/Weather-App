package com.example.weatherapp

object Utils {
    const val API_KEY = "bed7353d2cc5f158c99df83035608fd2"
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun checkValidInput(latitude: String, longitude: String): Boolean {
        if (latitude.isNotEmpty() && longitude.isNotEmpty()) {
            return true
        }
        return false
    }
}