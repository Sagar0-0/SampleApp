package com.sagar.sampleapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * from movieEntity")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Insert
    suspend fun insertAll(list: List<MovieEntity>)
}