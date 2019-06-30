package com.study.forecast.data.db.unitlocalized.future.detail

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 30-Jun-19.
 * @since 1.0.0
 */
data class ImperialDetailFutureWeatherEntry(
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "maxtempF")
    override val maxTemperature: Double,
    @ColumnInfo(name = "mintempF")
    override val minTemperature: Double,
    @ColumnInfo(name = "avgtempF")
    override val avgTemperature: Double,
    @ColumnInfo(name = "conditionText")
    override val conditionText: String,
    @ColumnInfo(name = "conditionIconUrl")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "maxwindMph")
    override val maxWindSpeed: Double,
    @ColumnInfo(name = "totalprecipIn")
    override val totalPrecipitation: Double,
    @ColumnInfo(name = "avgvisMiles")
    override val avgVisibilityDistance: Double,
    @ColumnInfo(name = "uv")
    override val uv: Double
) : UnitSpecificDetailFutureWeatherEntry