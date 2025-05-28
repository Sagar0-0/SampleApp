package com.sagar.sampleapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDB: RoomDatabase() {
    abstract fun getMovieDao(): MoviesDao
}