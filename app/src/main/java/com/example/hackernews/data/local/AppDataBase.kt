package com.example.hackernews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StoriesEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun storiesDao() : StoriesDao

}