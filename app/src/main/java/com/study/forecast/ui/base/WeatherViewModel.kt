package com.study.forecast.ui.base

import androidx.lifecycle.MutableLiveData
import com.almatime.connectmillion.android.utils.Logger
import com.study.forecast.data.db.entity.WeatherLocation
import com.study.forecast.data.provider.UnitProvider
import com.study.forecast.data.repo.ForecastRepo
import kotlinx.coroutines.launch

abstract class WeatherViewModel(
    protected val forecastRepo: ForecastRepo,
    unitProvider: UnitProvider
) : ScopedViewModel() {

    var unitSystem = unitProvider.getUnitSystem()

    val locationData = MutableLiveData<WeatherLocation>()

    fun loadWeatherLocation() {
        Logger.i { "[CurrWeatherViewModel] loadWeatherLocation before launch" }
        launch {
            val weatherLocation = forecastRepo.getWeatherLocation()
            weatherLocation?.let {
                locationData.postValue(it)
            }
            Logger.i { "[CurrWeatherViewModel] loadWeatherLocation launch FINISHED" }
        }
    }

}
