package com.study.forecast.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.almatime.connectmillion.android.utils.Logger
import com.study.forecast.data.network.response.CurrentWeatherResponse
import com.study.forecast.data.network.response.FutureWeatherResponse
import com.study.forecast.internal.NoInternetException

class WeatherNetworkDataSourceImpl(private val apixuWeatherApiService: ApixuWeatherApiService) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchCurrentWeather(location: String, langCode: String) {
        try {
            val fetchedCurrentWeather = apixuWeatherApiService.getCurrentWeather(location, langCode)
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoInternetException) {
            Logger.e(e)
        }
    }

    override suspend fun fetchFutureWeather(location: String, days: Int, langCode: String) {
        try {
            val fetchedFutureWeather = apixuWeatherApiService.getFutureWeather(location, days, langCode)
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        } catch (e: NoInternetException) {
            Logger.e(e)
        }
    }

}