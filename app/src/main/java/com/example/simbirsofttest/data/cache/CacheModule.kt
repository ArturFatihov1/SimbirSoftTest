package com.example.simbirsofttest.data.cache

import android.content.Context
import androidx.room.Room
import com.example.simbirsofttest.R

interface CacheModule {
    fun dao(): TaskDao

    class Base(applicationContext: Context) : CacheModule {
        private val database by lazy {
            Room.databaseBuilder(
                applicationContext,
                TaskDatabase::class.java,
                applicationContext.getString(R.string.app_name)
            ).build()
        }

        override fun dao(): TaskDao = database.dao()
    }
}