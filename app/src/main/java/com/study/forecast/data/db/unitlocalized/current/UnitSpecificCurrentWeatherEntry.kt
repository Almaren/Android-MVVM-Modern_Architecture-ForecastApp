package com.study.forecast.data.db.unitlocalized.current


/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 21-Jun-19.
 * @since 1.0.0
 */
interface UnitSpecificCurrentWeatherEntry {
    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
}