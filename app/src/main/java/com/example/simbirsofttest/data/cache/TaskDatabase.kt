package com.example.simbirsofttest.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskCache::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun dao(): TaskDao
}
