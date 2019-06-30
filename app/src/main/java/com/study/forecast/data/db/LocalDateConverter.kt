package com.study.forecast.data.db

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 28-Jun-19.
 * @since 1.0.0
 */
object LocalDateConverter {

    @TypeConverter
    @JvmStatic
    fun stringToDate(strDate: String?) = strDate?.let {
        LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(dateTime: LocalDate?) = dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE)

}