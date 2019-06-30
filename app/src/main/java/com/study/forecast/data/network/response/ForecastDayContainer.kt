package com.study.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import com.study.forecast.data.db.entity.FutureWeatherEntry


data class ForecastDayContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)