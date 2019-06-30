package com.study.forecast.data.db.entity

import androidx.room.ColumnInfo


data class Condition(
    @ColumnInfo(name = "Code")
    val code: Int,
    @ColumnInfo(name = "IconUrl")
    val icon: String,
    @ColumnInfo(name = "Text")
    val text: String
)