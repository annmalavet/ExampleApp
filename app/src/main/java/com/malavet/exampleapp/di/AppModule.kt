package com.malavet.exampleapp.di

import android.content.Context
import androidx.room.Room
import com.malavet.exampleapp.data.database.SchoolSATDao
import com.malavet.exampleapp.data.database.SchoolSATDatabase
import com.malavet.exampleapp.network.Endpoints
import com.malavet.exampleapp.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
* All dependency injection in this one module, as app grows should be separated for better organization/modular architecture
* */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideProfileDatabase(@ApplicationContext context: Context): SchoolSATDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            SchoolSATDatabase::class.java,
            "Profile.db"
        ).build()
    }

    @Provides
    fun provideBaseUrl(): String {
        return "url"
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson as the converter
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Endpoints {
        return retrofit.create(Endpoints::class.java)
    }

    @Provides
    fun provideNetworkService(): NetworkService {
        return NetworkService
    }

    @Provides
    fun provideDataDao(database: SchoolSATDatabase): SchoolSATDao {
        return database.schoolSATDao()
    }

}