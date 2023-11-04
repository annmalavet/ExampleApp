package com.malavet.exampleapp.data.models

import com.malavet.exampleapp.data.database.SchoolEntity
import kotlinx.serialization.Serializable

/*
* School Data Model
* */
@Serializable
data class School(
    val dbn: String,
    val school_name: String
)


/* Function that maps the Schools data model to the Entity for database s
* */
fun mapSchoolModelToEntity(dataModel: School): SchoolEntity {
    return SchoolEntity(
        dbn = dataModel.dbn,
        school_name = dataModel.school_name
    )
}

