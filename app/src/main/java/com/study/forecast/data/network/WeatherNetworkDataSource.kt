package com.study.forecast.data.network

import androidx.lifecycle.LiveData
import com.study.forecast.data.network.response.CurrentWeatherResponse
import com.study.forecast.data.network.response.FutureWeatherResponse

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 21-Jun-19.
 * @since 1.0.0
 */
interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(location: String, langCode: String)
    suspend fun fetchFutureWeather(location: String, days: Int, langCode: String)

}