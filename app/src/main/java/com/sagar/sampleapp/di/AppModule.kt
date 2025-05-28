package com.sagar.sampleapp.di

import android.content.Context
import androidx.room.Room
import com.sagar.sampleapp.data.db.MovieDB
import com.sagar.sampleapp.data.repo.MoviesRepo
import com.sagar.sampleapp.data.repo.MoviesRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesDB(@ApplicationContext context: Context): MovieDB {
        return Room.databaseBuilder(
            context = context,
            klass = MovieDB::class.java,
            name = "movie-db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesMoviesRepo(db: MovieDB): MoviesRepo {
        return MoviesRepoImpl(db.getMovieDao())
    }
}