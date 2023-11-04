package com.malavet.exampleapp.data.models

import com.malavet.exampleapp.data.database.SATEntity
import com.malavet.exampleapp.data.database.SchoolEntity
import kotlinx.serialization.Serializable

/*
* SAT Data Model
* */
@Serializable
data class SAT(
    val dbn: String,
    val school_name: String? = null,
    val num_of_sat_test_takers: String? = null,
    val sat_critical_reading_avg_score: String? = null,
    val sat_math_avg_score: String? = null,
    val sat_writing_avg_score: String? = null,
)

/*
* Function that maps the SAT data model to the Entity for database
* */
fun mapSATModelToEntity(dataModel: SAT): SATEntity {
    return SATEntity(
        dbn = dataModel.dbn,
        school_name = dataModel.school_name,
        num_of_sat_test_takers = dataModel.num_of_sat_test_takers,
        sat_critical_reading_avg_score = dataModel.sat_critical_reading_avg_score,
        sat_writing_avg_score = dataModel.sat_writing_avg_score,
        sat_math_avg_score = dataModel.sat_math_avg_score

    )
}