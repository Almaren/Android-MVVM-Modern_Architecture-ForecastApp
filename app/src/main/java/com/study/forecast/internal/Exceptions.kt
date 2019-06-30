package com.study.forecast.internal

import java.io.IOException

/**
 * @author Alexander Khrapunsky
 * @version 1.0.0, 21-Jun-19.
 * @since 1.0.0
 */
class NoInternetException : IOException()

class LocationPermissionNotGrantedException() : Exception()

class DateNotFoundException() : Exception()