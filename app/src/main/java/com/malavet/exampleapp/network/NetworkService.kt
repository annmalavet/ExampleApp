package com.malavet.exampleapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/*
* All Hilt Injected components in this one place, for simplicity
* */
@Module
@InstallIn(SingletonComponent::class)
object NetworkService  {

    fun provideBaseUrl(): String {
        return "https://data.cityofnewyork.us/resource/"
    }

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(provideBaseUrl())
        .client(client)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    var schoolApi: Endpoints = retrofit.create(Endpoints::class.java)
}
