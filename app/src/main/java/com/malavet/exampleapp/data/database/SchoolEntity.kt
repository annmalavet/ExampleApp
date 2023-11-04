package com.malavet.exampleapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Entity for School
* */
@Entity
    (tableName = "school_entity")
data class SchoolEntity(
    @PrimaryKey
    val dbn: String,
    val school_name: String
)