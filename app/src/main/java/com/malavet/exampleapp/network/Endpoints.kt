package com.malavet.exampleapp.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoints {

    @GET("s3k6-pzi2.json")
    suspend fun getSchools(): Response<ResponseBody>

    @GET("f9bf-2cp4.json")
    suspend fun getSATFromSchool(@Query("dbn") dbnOfSchool: String):  Response<ResponseBody>


}