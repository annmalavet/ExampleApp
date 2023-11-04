package com.malavet.exampleapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.malavet.exampleapp.data.models.SAT
import com.malavet.exampleapp.data.models.School


/*
* Queries for database
* All Schools or All SAT info for WHERE clause [school dbn]
* */
@Dao
interface SchoolSATDao {

    @Query("SELECT * FROM school_entity")
    fun getAllSchools(): List<School>

    @Query("SELECT * FROM sat_entity where dbn = :dbn")
    fun getSATData(dbn: String): List<SAT>

    /**
     * Inserts [schoolEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreSchool(schoolEntity: List<SchoolEntity>): List<Long>

    /**
     * Inserts [SATEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreSAT(satEntity: List<SATEntity>): List<Long>

}