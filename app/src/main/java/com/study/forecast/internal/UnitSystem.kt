package com.study.forecast.internal

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 23-Jun-19.
 * @since 1.0.0
 */
enum class UnitSystem(
    val temperatureUnit: String,
    val percipitiationVolume: String,
    val speed: String,
    val distance: String
) {
    METRIC("°C", "mm", "kph", "km"),
    IMPERIAL("°F", "in", "mph", "mi")
}