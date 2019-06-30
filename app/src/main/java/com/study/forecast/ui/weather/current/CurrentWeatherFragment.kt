package com.study.forecast.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.almatime.connectmillion.android.utils.Logger
import com.study.forecast.R
import com.study.forecast.utils.glide.GlideApp
import kotlinx.android.synthetic.main.current_weather_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class CurrentWeatherFragment : Fragment() {

    private val viewModel: CurrentWeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.i { "[CurrWeatherFragment] onCreateView" }
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Logger.i { "[CurrWeatherFragment] onActivityCreated viewModel = $viewModel" }
        super.onActivityCreated(savedInstanceState)
        loadData()
        bindUI()
    }

    private fun loadData() {
        viewModel.loadCurrWeather()
        viewModel.loadWeatherLocation()
    }

    private fun bindUI() {
        viewModel.weatherData.observe(this, Observer {
            Logger.i { "[CurrWeatherFragment] weather.observe = ${it}" }
            if (it == null) return@Observer

            currGorupLoading.visibility = View.GONE
            updateDateToToday()
            updateTemperature(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePercipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this)
                .load("http:${it.conditionIconUrl}")
                .into(currImgWeather)
        })

        viewModel.locationData.observe(this, Observer {
            Logger.i { "[CurrWeatherFragment] locationData.observe = ${it}" }
            if (it == null) return@Observer
            updateLocation(it.name)
        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(tem: Double, feelsLike: Double) {
        val unitAbbrev = viewModel.unitSystem.temperatureUnit
        currTvTem.text = "$tem$unitAbbrev"
        currTvTemFeel.text = "$feelsLike $unitAbbrev"
    }

    private fun updateCondition(condition: String) {
        currTvCondition.text = condition
    }

    private fun updatePercipitation(percipVolume: Double) {
        val unitAbbrev = viewModel.unitSystem.percipitiationVolume
        currTvPercip.text = "Percipitation: $percipVolume $unitAbbrev"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbrev = viewModel.unitSystem.speed
        currTvWind.text = "Wind: $windDirection, $windSpeed $unitAbbrev"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbrev = viewModel.unitSystem.distance
        currTvVisibility.text = "Visibility: $visibilityDistance $unitAbbrev"
    }

}
