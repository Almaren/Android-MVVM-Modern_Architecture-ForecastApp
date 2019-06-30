package com.study.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import com.study.forecast.data.db.entity.WeatherLocation


data class FutureWeatherResponse(
    @SerializedName("forecast")
    val futureWeatherEntries: ForecastDayContainer,
    @SerializedName("location")
    val location: WeatherLocation
)