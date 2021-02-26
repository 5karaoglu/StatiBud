package com.uhi5d.spotibud.util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uhi5d.spotibud.model.SearchHistory
import com.uhi5d.spotibud.model.TrackFinderTracks
import com.uhi5d.spotibud.repository.Dao
import com.uhi5d.spotibud.repository.TrackFinderDao

@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dao(): Dao
}

@Database(entities = [TrackFinderTracks::class], version = 1, exportSchema = false)
abstract class TrackFinderDatabase: RoomDatabase() {
    abstract fun trackFinderDao(): TrackFinderDao
}