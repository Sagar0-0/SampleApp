package com.sagar.sampleapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieEntity")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val movieName: String,
    val releaseDate: String,
    val watchStatus: Int,
) {
    companion object {
        const val WATCH_STATUS_UNWATCHED = 0
        const val WATCH_STATUS_WATCHLIST = 1
        const val WATCH_STATUS_WATCHED = 2
    }
}
