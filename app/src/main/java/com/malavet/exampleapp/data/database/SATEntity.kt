package com.malavet.exampleapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Entity for SAT
* */
@Entity
    (tableName = "sat_entity")
data class SATEntity(
    @PrimaryKey
    val dbn: String,
    @ColumnInfo(defaultValue = "")
    val school_name: String?,
    @ColumnInfo(defaultValue = "")
    val sat_critical_reading_avg_score: String?,
    @ColumnInfo(defaultValue = "")
    val sat_writing_avg_score: String?,
    @ColumnInfo(defaultValue = "")
    val sat_math_avg_score: String?,
    @ColumnInfo(defaultValue = "")
    val num_of_sat_test_takers:String?
)