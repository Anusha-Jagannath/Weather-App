package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.R
import com.example.weatherapp.Utils.API_KEY
import com.example.weatherapp.Utils.BASE_URL
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.network.WeatherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData = _weatherData as LiveData<WeatherData>

    private val _loader = MutableLiveData<Boolean>()
    val loader = _loader as LiveData<Boolean>

    private val _error = MutableLiveData<String>()
    val error = _error as LiveData<String>


    fun setWeatherData(value: WeatherData) {
        _weatherData.value = value

    }

    /**
     * method to fetch the weather data from the api
     * @param latitude
     * @param longitude
     * @param context
     */
    fun getWeatherData(latitude: Double, longitude: Double, context: Context) {
        _loader.value = true
        _error.value = ""
        val weatherApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(WeatherApi::class.java)

        val weatherResponse = weatherApi.getWeatherData(latitude, longitude, API_KEY)
        weatherResponse.enqueue(object : Callback<WeatherData?> {
            override fun onResponse(call: Call<WeatherData?>, response: Response<WeatherData?>) {
                response.body()?.let { setWeatherData(it) }
                _loader.value = false
                if (response.code() == 404 || !response.isSuccessful) {
                    _error.value = context.getString(R.string.please_enter_valid_data)
                }
            }

            override fun onFailure(call: Call<WeatherData?>, error: Throwable) {
                _loader.value = false
                _error.value = context.getString(R.string.please_try_again_message)
            }
        })

    }
}