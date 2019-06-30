package com.study.forecast.data.provider

import com.study.forecast.data.db.entity.WeatherLocation

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 27-Jun-19.
 * @since 1.0.0
 */
interface LocationProvider {

    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String

}