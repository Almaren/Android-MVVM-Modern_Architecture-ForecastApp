package com.study.forecast.data.db.unitlocalized.future.list

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 28-Jun-19.
 * @since 1.0.0
 */
data class MetricSimpleFutureWeatherEntry (
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "avgtempC")
    override val avgTemperature: Double,
    @ColumnInfo(name = "conditionText")
    override val conditionText: String,
    @ColumnInfo(name = "conditionIconUrl")
    override val conditionIconUrl: String
) : UnitSpecificSimpleFutureWeatherEntry