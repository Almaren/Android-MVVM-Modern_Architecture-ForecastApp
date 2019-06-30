package com.study.forecast.ui.weather.future.detail

import androidx.lifecycle.MutableLiveData
import com.almatime.connectmillion.android.utils.Logger
import com.study.forecast.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import com.study.forecast.data.provider.UnitProvider
import com.study.forecast.data.repo.ForecastRepo
import com.study.forecast.internal.UnitSystem
import com.study.forecast.ui.base.WeatherViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    forecastRepo: ForecastRepo,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepo, unitProvider) {

    val weatherDetailsData = MutableLiveData<UnitSpecificDetailFutureWeatherEntry>()

    fun loadWeatherDetails(forDate: LocalDate) {
        Logger.i { "[FutureDetailWeatherViewModel] loadWeatherDetails before launch" }
        launch {
            val weatherDetails = forecastRepo.getFutureWeatherByDate(unitSystem == UnitSystem.METRIC, forDate)
            weatherDetailsData.postValue(weatherDetails)
            Logger.i { "[FutureDetailWeatherViewModel] loadWeatherDetails FINISHED" }
        }
    }

}
