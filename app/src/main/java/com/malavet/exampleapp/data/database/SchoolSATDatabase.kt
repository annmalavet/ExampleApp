package com.malavet.exampleapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [SATEntity::class, SchoolEntity::class], version = 1, exportSchema = false)
abstract class SchoolSATDatabase : RoomDatabase() {
    abstract fun schoolSATDao(): SchoolSATDao
}