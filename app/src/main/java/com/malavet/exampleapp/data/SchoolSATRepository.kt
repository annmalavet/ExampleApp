package com.malavet.exampleapp.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.malavet.exampleapp.data.database.SchoolSATDao
import com.malavet.exampleapp.data.models.SAT
import com.malavet.exampleapp.data.models.School
import com.malavet.exampleapp.data.models.mapSATModelToEntity
import com.malavet.exampleapp.data.models.mapSchoolModelToEntity
import com.malavet.exampleapp.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for both the Schools and SAT
 **/

class SchoolSATRepository @Inject constructor(
    private val apiService: NetworkService,
    private val dataDao: SchoolSATDao
    ) {

/*
* Get Schools from API and put in Room database
* If the network call fails, get the Schools from the database
*/
    suspend fun schools(): List<School>
    {
        var listOfSchools:List<School>
        withContext(Dispatchers.Default) {
            try {
                listOfSchools = getSchoolsFromJson()
                val entities = listOfSchools.map { d -> mapSchoolModelToEntity(d) }
                dataDao.insertOrIgnoreSchool(entities)
            } catch (e: Exception){
                listOfSchools = dataDao.getAllSchools()
            }
        }
        return listOfSchools
    }

 /*
* Get SAT info from API and put in Room database
* If the network call fails, get the SAT info from the database
*/
    suspend fun sats(school: String):List<SAT>
    {
        var sat: List<SAT>
        withContext(Dispatchers.Default) {
            try{
                sat = getSATFromJson(school)
                val entities = sat.map { d -> mapSATModelToEntity(d) }
                dataDao.insertOrIgnoreSAT(entities)
            } catch (e: Exception) {
                sat = dataDao.getSATData(school)
            }
        }
        return sat
    }

 /*
* JSON response mapped to data model for SAT
* */
    suspend fun getSATFromJson(school: String): List<SAT> {
        val json = apiService.schoolApi.getSATFromSchool(school).body()?.string() ?: ""
        val satDataList: List<SAT> = Gson().fromJson(json, object : TypeToken<List<SAT>>() {}.type)
        return satDataList
    }

    /*
* JSON response mapped to data model for School
* */
    suspend fun getSchoolsFromJson(): List<School> {
        val json = apiService.schoolApi.getSchools().body()?.string() ?: ""
        return Gson().fromJson(json, Array<School>::class.java).toList()
    }










}