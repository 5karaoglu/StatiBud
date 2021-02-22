package com.example.contestifyfirsttry.util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contestifyfirsttry.model.SearchHistory
import com.example.contestifyfirsttry.model.TrackFinderTracks

@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dao(): Dao
}

@Database(entities = [TrackFinderTracks::class], version = 1, exportSchema = false)
abstract class TrackFinderDatabase: RoomDatabase() {
    abstract fun trackFinderDao(): TrackFinderDao
}