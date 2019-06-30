package com.study.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import com.study.forecast.data.db.entity.CurrentWeatherEntry
import com.study.forecast.data.db.entity.WeatherLocation


data class CurrentWeatherResponse(
    @SerializedName("location")
    val location: WeatherLocation,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)