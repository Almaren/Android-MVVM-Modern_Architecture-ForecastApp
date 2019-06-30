package com.study.forecast.ui.weather.future.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.study.forecast.R
import com.study.forecast.data.db.LocalDateConverter
import com.study.forecast.internal.DateNotFoundException
import com.study.forecast.utils.glide.GlideApp
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureDetailWeatherFragment : Fragment() {

    private val viewModel: FutureDetailWeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { FutureDetailWeatherFragmentArgs.fromBundle(it) }
        val dateDetails = LocalDateConverter.stringToDate(safeArgs?.dateString) ?: throw DateNotFoundException()

        loadData(dateDetails)
        bindUI()
    }

    private fun loadData(dateFor: LocalDate) {
        viewModel.loadWeatherDetails(dateFor)
        viewModel.loadWeatherLocation()
    }

    private fun bindUI() {
        viewModel.locationData.observe(this, Observer {
            if (it == null) return@Observer
            updateLocation(it.name)
        })

        viewModel.weatherDetailsData.observe(this, Observer {
            if (it == null) return@Observer

            updateDate(it.date)
            updateTemperatures(it.avgTemperature,
                it.minTemperature, it.maxTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.totalPrecipitation)
            updateWindSpeed(it.maxWindSpeed)
            updateVisibility(it.avgVisibilityDistance)
            updateUv(it.uv)

            GlideApp.with(this@FutureDetailWeatherFragment)
                .load("http:" + it.conditionIconUrl)
                .into(imgFutureDetailsCondition)
        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDate(date: LocalDate) {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    private fun updateTemperatures(temperature: Double, min: Double, max: Double) {
        val unitAbbreviation = viewModel.unitSystem.temperatureUnit
        tvFutureDetailsTemperature.text = "$temperature$unitAbbreviation"
        tvFutureDetailsMinMaxTemperature.text = "Min: $min$unitAbbreviation, Max: $max$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        tvFutureDetailsCondition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = viewModel.unitSystem.percipitiationVolume
        tvFutureDetailsPercipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWindSpeed(windSpeed: Double) {
        val unitAbbreviation = viewModel.unitSystem.speed
        tvFutureDetailsWind.text = "Wind speed: $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = viewModel.unitSystem.distance
        tvFutureDetailsVisibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

    private fun updateUv(uv: Double) {
        tvFutureDetailsUv.text = "UV: $uv"
    }

}
