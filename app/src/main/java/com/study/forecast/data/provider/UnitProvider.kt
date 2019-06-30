package com.study.forecast.data.provider

import com.study.forecast.internal.UnitSystem

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 24-Jun-19.
 * @since 1.0.0
 */
interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}