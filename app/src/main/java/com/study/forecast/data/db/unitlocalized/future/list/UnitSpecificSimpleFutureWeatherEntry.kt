package com.study.forecast.data.db.unitlocalized.future.list

import org.threeten.bp.LocalDate

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 28-Jun-19.
 * @since 1.0.0
 */
interface UnitSpecificSimpleFutureWeatherEntry {
    val date: LocalDate
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
}