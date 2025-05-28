package com.sagar.sampleapp.data.repo

import com.sagar.sampleapp.data.db.MovieEntity
import com.sagar.sampleapp.data.db.MoviesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class MoviesRepoImpl(
    private val moviesDao: MoviesDao
) : MoviesRepo {
    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return moviesDao.getAllMovies()
            .onStart {
                moviesDao.insertAll(
                    list = listOf(
                        MovieEntity(
                            id = 1,
                            movieName = "Brian Garrett",
                            releaseDate = "cu",
                            watchStatus = MovieEntity.WATCH_STATUS_UNWATCHED,
                        ),
                        MovieEntity(
                            id = 2,
                            movieName = "Brian",
                            releaseDate = "cu",
                            watchStatus = MovieEntity.WATCH_STATUS_UNWATCHED,
                        ),
                        MovieEntity(
                            id = 3,
                            movieName = "Garrett",
                            releaseDate = "cu",
                            watchStatus = MovieEntity.WATCH_STATUS_UNWATCHED,
                        ),
                        MovieEntity(
                            id = 4,
                            movieName = "Sagar",
                            releaseDate = "cu",
                            watchStatus = MovieEntity.WATCH_STATUS_UNWATCHED,
                        ),
                        MovieEntity(
                            id = 5,
                            movieName = "Brian Sagar",
                            releaseDate = "cu",
                            watchStatus = MovieEntity.WATCH_STATUS_UNWATCHED,
                        ),
                        MovieEntity(
                            id = 6,
                            movieName = "Android Garrett",
                            releaseDate = "cu",
                            watchStatus = MovieEntity.WATCH_STATUS_UNWATCHED,
                        ),
                        MovieEntity(
                            id = 7,
                            movieName = "Brian Android",
                            releaseDate = "cu",
                            watchStatus = MovieEntity.WATCH_STATUS_UNWATCHED,
                        ),
                        MovieEntity(
                            id = 8,
                            movieName = "Brian Swiggy",
                            releaseDate = "cu",
                            watchStatus = MovieEntity.WATCH_STATUS_UNWATCHED,
                        ),
                        MovieEntity(
                            id = 9,
                            movieName = "Swiggy Garrett",
                            releaseDate = "cu",
                            watchStatus = MovieEntity.WATCH_STATUS_UNWATCHED,
                        )
                    )
                )
            }
    }
}