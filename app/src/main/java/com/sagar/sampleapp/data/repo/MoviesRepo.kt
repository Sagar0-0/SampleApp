package com.sagar.sampleapp.data.repo

import com.sagar.sampleapp.data.db.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MoviesRepo {
    fun getAllMovies(): Flow<List<MovieEntity>>

    suspend fun moveToWatchList(movie: MovieEntity)
    suspend fun moveToWatched(movie: MovieEntity)
}