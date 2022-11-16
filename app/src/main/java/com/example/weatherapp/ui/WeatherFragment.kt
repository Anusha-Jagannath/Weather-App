package com.example.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.Utils
import com.example.weatherapp.databinding.WeatherFragmentBinding
import com.example.weatherapp.viewmodel.WeatherViewModel

class WeatherFragment : Fragment() {

    private lateinit var binding: WeatherFragmentBinding
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weather_fragment, container, false)
        binding = WeatherFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        binding.submitBtn.setOnClickListener {
            val latitude = binding.latitudeInput.text.toString().trim()
            val longitude = binding.longitudeInput.text.toString().trim()
            if (Utils.checkValidInput(latitude,longitude)) {
                viewModel.getWeatherData(latitude.toDouble(), longitude.toDouble(),requireContext())
            } else {
                clearWeatherData()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        observeWeatherData()
        return view
    }

    private fun clearWeatherData() {
       binding.cardview.visibility = View.GONE
    }

    /**
     * method to observe the change in data
     */
    private fun observeWeatherData() {
        viewModel.weatherData.observe(viewLifecycleOwner) { data ->
            binding.cardview.visibility = View.VISIBLE
            binding.errorTv.visibility = View.GONE
            val celsius = data.main?.temp?.minus(273.15)
            val formattedTemp = String.format("%.3f", celsius)
            binding.timeResult.text = "Time Zone: ${data.timezone}"
            binding.temperatureResult.text = "Temperature: $formattedTemp celsius"
            binding.humidityResult.text = "Humidity: ${data.main?.humidity.toString()}%"
            binding.windResult.text = "Wind speed ${data.wind?.speed.toString()} miles/hr"

        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()){
                binding.cardview.visibility = View.GONE
                binding.errorTv.visibility = View.VISIBLE
                binding.errorTv.text = error
            }
        }
        viewModel.loader.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE
            }
        }
    }
}